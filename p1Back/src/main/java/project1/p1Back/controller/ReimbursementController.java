package project1.p1Back.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import project1.p1Back.entity.Reimbursement;
import project1.p1Back.entity.User;
import project1.p1Back.exception.ReimbursementNotFoundException;
import project1.p1Back.exception.UnauthorizedActionException;
import project1.p1Back.service.JwtService;
import project1.p1Back.service.ReimbursementService;
import project1.p1Back.service.UserService;

@RestController
@RequestMapping("/reimbursements")
public class ReimbursementController {

    private final ReimbursementService reimbursementService;
    private final JwtService jwtService;
    private final UserService userService;

    public ReimbursementController(ReimbursementService reimbursementService, JwtService jwtService, UserService userService) {
        this.reimbursementService = reimbursementService;
        this.jwtService = jwtService;
        this.userService = userService;
    }


    @PostMapping("/create")
public ResponseEntity<?> createReimbursement(@RequestBody Reimbursement reimbursement,@RequestHeader(value = "Token") String token) {
    try {
        User user = userService.findUserById(jwtService.extractUserId(token.replace("Bearer ", "")));
        Reimbursement createdReimbursement = reimbursementService.createReimbursement(new Reimbursement(reimbursement.getDescription(), reimbursement.getAmount(), user));
        return ResponseEntity.ok(createdReimbursement);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (UnauthorizedActionException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(("Error: Internal server error"));
    }
}


    @PatchMapping("/approve") 
    public ResponseEntity<?> approveReimbursement(@RequestBody Map<String, Integer> request, @RequestHeader(value = "Token") String token) {
        try {
            User user = userService.findUserById(jwtService.extractUserId(token.replace("Bearer ", "")));
            Reimbursement approvedReimbursement = reimbursementService.approveReimbursement(user.getUserId(), request.get("reimId"));
            return ResponseEntity.ok(approvedReimbursement);
        } catch (UnauthorizedActionException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (ReimbursementNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Internal server error");
        }
    }
}
