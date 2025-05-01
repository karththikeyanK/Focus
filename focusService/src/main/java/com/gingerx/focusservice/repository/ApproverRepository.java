package com.gingerx.focusservice.repository;

import com.gingerx.focusservice.dto.ApproverResponse;
import com.gingerx.focusservice.entity.Approver;
import com.gingerx.focusservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApproverRepository extends JpaRepository<Approver, Long> {

    boolean existsByUserId(Long userId);
    boolean existsByUserIdAndApproverId(Long userId, Long approverId);

    List<Approver> findByUser(User user);

    @Query("SELECT new com.gingerx.focusservice.dto.ApproverResponse(" +
            "a.id, u.id, CONCAT(u.firstName, ' ', u.lastName), " +
            "ap.id, CONCAT(ap.firstName, ' ', ap.lastName), " +
            "a.status,a.deviceName) " +
            "FROM Approver a " +
            "JOIN a.user u " +
            "JOIN a.approver ap " +
            "WHERE a.approver = :approver")
    List<ApproverResponse> findAllApproversByApprover(User approver);


    @Query("SELECT a FROM Approver a WHERE a.user.id = :userId AND a.approver.email = :email")
    Optional<Approver> findByUser_IdAndApprover_Email(Long userId, String email);

    @Query("SELECT a FROM Approver a WHERE a.user.id = :userId AND a.approver.id = :approverId")
    Optional<Approver> findByUser_IdAndApprover_Id(Long userId, Long approverId);

    @Query("SELECT a FROM Approver a WHERE a.approver.id = :userId")
    Optional<Approver> findByApproverUserId(@Param("userId") Long userId);
}
