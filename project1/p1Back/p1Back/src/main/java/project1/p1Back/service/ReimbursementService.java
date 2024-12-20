package project1.p1Back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import project1.p1Back.entity.Reimbursement;
import project1.p1Back.entity.User;
import project1.p1Back.exception.ReimbursementNotFoundException;
import project1.p1Back.exception.UnauthorizedActionException;
import project1.p1Back.repository.ReimbursementRepository;
import project1.p1Back.repository.UserRepository;

@Service
public class ReimbursementService {

    private final ReimbursementRepository reimbursementRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReimbursementService(ReimbursementRepository reimbursementRepository, UserRepository userRepository) {
        this.reimbursementRepository = reimbursementRepository;
        this.userRepository = userRepository;
    }


    public Reimbursement createReimbursement(Reimbursement reimbursement) {
        if (reimbursement.getUser().getRole().getName().equals("employee")) {
            String validationMessage = validateReimbursementInputs(reimbursement.getDescription(), reimbursement.getAmount(), reimbursement.getUser());
            if (validationMessage != null) {
                throw new IllegalArgumentException(validationMessage);
            }
            Reimbursement reimbursement2 = new Reimbursement(reimbursement.getDescription(), reimbursement.getAmount(),"Pending", reimbursement.getUser());
            return reimbursementRepository.save(reimbursement2);
        } else {
            throw new UnauthorizedActionException("Only employees can create reimbursements.");
        }
    }

    private String validateReimbursementInputs(String description, Double amount, User user) {
        // Validation checks combined into one block
        if (description == null || description.isBlank()) {
            return "Description cannot be blank.";
        }
        if (description.length() > 255) {
            return "Description cannot exceed 255 characters.";
        }
        if (amount == null || amount <= 0) {
            return "Amount must be greater than zero.";
        }
        if (!userRepository.existsById(user.getUserId())) {
            return "User does not exist in the system.";
        }
        return null;
    }
    public Reimbursement approveReimbursement(Integer userId, Integer reimId) {
        User user = userRepository.findById(userId)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!user.getRole().getName().equals("manager")) {
            throw new UnauthorizedActionException("Only managers can approve reimbursements.");
        }
        
        Reimbursement reimbursement = reimbursementRepository.findById(reimId)
        .orElseThrow(() -> new ReimbursementNotFoundException("Reimbursement not found"));

        if (!reimbursement.getStatus().equals("Pending")) {
            throw new IllegalStateException("Only pending reimbursements can be approved.");
        }

        reimbursement.setStatus("Approved");
        return reimbursementRepository.save(reimbursement);
            
        }

        
    }

