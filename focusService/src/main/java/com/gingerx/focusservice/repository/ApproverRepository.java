package com.gingerx.focusservice.repository;

import com.gingerx.focusservice.entity.Approver;
import com.gingerx.focusservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApproverRepository extends JpaRepository<Approver, Long> {

    boolean existsByUserId(Long userId);
    boolean existsByUserIdAndApproverId(Long userId, Long approverId);

    List<Approver> findByUser(User user);
}
