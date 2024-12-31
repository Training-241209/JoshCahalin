package project1.p1Back.service;



import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import project1.p1Back.entity.AuthenticationResponse;
import project1.p1Back.entity.Role;
import project1.p1Back.entity.User;
import project1.p1Back.exception.DuplicateUsernameException;
import project1.p1Back.repository.RoleRepository;
import project1.p1Back.repository.UserRepository;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
    }
    private Role getDefaultRole() {
        return roleRepository.findByName("employee")
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    public AuthenticationResponse register(User request){
        if (request == null) {
                    throw new IllegalArgumentException("User cannot be null");
                }

                String username = request.getUsername();
                String password = request.getPassword();

                // Check if the username is blank
                if (username.isBlank()) {
                    throw new IllegalArgumentException("Username cannot be blank");
                }

                // Check if the password meets the length requirement
                if (password.length() < 4) {
                    throw new IllegalArgumentException("Password must be at least 4 characters long");
                }

                // Check if a user with that username already exists
                if (userRepository.findByUsername(username).isPresent()) {
                    throw new DuplicateUsernameException("Username already exists");
                }
        Role role = request.getRole();
        if (role == null) {
            role = getDefaultRole();
        } else {
            role = roleRepository.findById(role.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        }
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        user = userRepository.save(user);
        return new AuthenticationResponse("User registered successfully");

    }

    public AuthenticationResponse login(User request){
       try {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
    } catch (BadCredentialsException e) {
        throw new RuntimeException("Invalid username or password"); 
    }

    User user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new RuntimeException("Invalid username or password"));

    String token = jwtService.generateToken(user);
    return new AuthenticationResponse(token);
}
}
