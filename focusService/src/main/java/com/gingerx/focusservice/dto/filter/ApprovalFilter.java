package com.gingerx.focusservice.dto.filter;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalFilter {
    private Long userId;
    private Long approverId;
    private Long restrictedAppId;
    private String status;
    private LocalDateTime scheduledTime;
    private LocalDateTime scheduledEndTime;
    private LocalDateTime createdAt;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @Min(value = 0)
    private int pageNumber = 0;
    @Min(value = 1)
    private int pageSize = 20;
}
