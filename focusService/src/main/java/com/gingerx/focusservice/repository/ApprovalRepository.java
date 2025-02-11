package com.gingerx.focusservice.repository;

import com.gingerx.focusservice.entity.Approval;
import com.gingerx.focusservice.entity.RestrictedApp;
import com.gingerx.focusservice.entity.User;
import com.gingerx.focusservice.enums.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ApprovalRepository extends JpaRepository<Approval, Long> {

    @Query("SELECT a FROM Approval a WHERE a.restrictedApp = :restrictedApp "
            + "AND a.user = :user "
            + "AND a.status = 'APPROVED' "
            + "AND (a.scheduledTime < :scheduledEndTime AND :scheduledTime < a.scheduledTime )")
    List<Approval> findConflictingApprovalsByUserAndApp(@Param("restrictedApp") RestrictedApp restrictedApp,
                                                        @Param("user") User user,
                                                        @Param("scheduledTime") LocalDateTime scheduledTime,
                                                        @Param("scheduledEndTime") LocalDateTime scheduledEndTime);

    @Query("SELECT a FROM Approval a WHERE a.restrictedApp = :restrictedApp "
            + "AND a.user = :user "
            + "AND a.status = 'APPROVED' "
            + "AND a.id != :id "
            + "AND (a.scheduledTime < :scheduledEndTime AND :scheduledTime < a.scheduledTime )")
    List<Approval> findConflictingApprovalsByUserAndAppAndId(@Param("restrictedApp") RestrictedApp restrictedApp,
                                                        @Param("user") User user,
                                                        @Param("scheduledTime") LocalDateTime scheduledTime,
                                                        @Param("scheduledEndTime") LocalDateTime scheduledEndTime
                                                        ,@Param("id") Long id);

    List<Approval> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);

    List<Approval> findByUserAndStatusOrderByCreatedAtDesc(User user, Status status, Pageable pageable);

    List<Approval> findByUserAndScheduledTimeBetweenOrderByScheduledTimeDesc(User user, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
