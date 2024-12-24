package project1.p1Back.service;

import project1.p1Back.entity.User;
import project1.p1Back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;

    // Constructor injection for UserRepository
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserById(int userId){
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        return userRepository.findByUsername(username)
                    .orElseThrow(()->new UsernameNotFoundException("User not found"));
    }
}
