package project1.p1Back.repository;

import project1.p1Back.entity.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReimbursementRepository extends JpaRepository<Reimbursement, Integer> {

}
