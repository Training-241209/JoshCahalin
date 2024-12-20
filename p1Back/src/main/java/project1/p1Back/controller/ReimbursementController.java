package project1.p1Back.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import project1.p1Back.dto.ApproveRequest;
import project1.p1Back.entity.Reimbursement;
import project1.p1Back.exception.ReimbursementNotFoundException;
import project1.p1Back.exception.UnauthorizedActionException;
import project1.p1Back.service.ReimbursementService;

@RestController
@RequestMapping("/reimbursements")
public class ReimbursementController {

    private final ReimbursementService reimbursementService;

    public ReimbursementController(ReimbursementService reimbursementService) {
        this.reimbursementService = reimbursementService;
    }


    @PostMapping("/create")
public ResponseEntity<?> createReimbursement(@RequestBody Reimbursement reimbursement) {
    try {
        Reimbursement createdReimbursement = reimbursementService.createReimbursement(reimbursement);
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
    public ResponseEntity<?> approveReimbursement(@RequestBody ApproveRequest approveRequest) {
        try {
            Reimbursement approvedReimbursement = reimbursementService.approveReimbursement(approveRequest.getUserId(), approveRequest.getReimId());
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
