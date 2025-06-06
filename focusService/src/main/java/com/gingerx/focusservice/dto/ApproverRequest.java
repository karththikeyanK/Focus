package com.gingerx.focusservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApproverRequest {
    private Long userId;
    private Long approverId;
    private String status;
    private String vCode;
    private String vCodeTime;
    private String deviceName;
}
