package com.gingerx.focusservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApprovalResponse {
    private Long id;
    private LocalDateTime scheduledTime;
    private DurationDto requestDuration;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status;
    private Long userId;
    private Long approverId;
    private Long restrictedAppId;
}
