package com.gingerx.focusservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalRequest {
    private LocalDateTime scheduledTime;
    private DurationDto requestDuration;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status;
    private Long userId;
    private Long approverId;
    private Long restrictedAppId;
}
