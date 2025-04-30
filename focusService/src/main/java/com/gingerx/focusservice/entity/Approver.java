package com.gingerx.focusservice.entity;

import com.gingerx.focusservice.enums.ActiveStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "approvers"
)
public class Approver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "approver_id")
    private User approver;

    @Column(name = "status")
    private ActiveStatus status;

    @Column(name = "v_code")
    private String vCode;

    @Column(name = "v_code_time")
    private LocalDateTime vCodeTime;

    @Column(name = "device_name")
    private String deviceName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
