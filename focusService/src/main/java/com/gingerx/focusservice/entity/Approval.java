package com.gingerx.focusservice.entity;


import com.gingerx.focusservice.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "approvals"
)
public class Approval {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "scheduled_time")  // this is the time when the request is scheduled to be approved IN UTC
        private LocalDateTime scheduledTime;

        @Column(name = "request_duration")
        private Duration requestDuration; // this is the duration for which the request is valid

        @Column(name = "created_at")
        private LocalDateTime createdAt;

        @Column(name = "updated_at")
        private LocalDateTime updatedAt;

        @Column(name = "status")
        @Enumerated(EnumType.STRING)
        private Status status;   // APPROVED, PENDING, REJECTED, EXPIRED

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

        @ManyToOne
        @JoinColumn(name = "approver_id")
        private Approver approver;

        @ManyToOne
        @JoinColumn(name = "restricted_app_id")
        private RestrictedApp restrictedApp;

}