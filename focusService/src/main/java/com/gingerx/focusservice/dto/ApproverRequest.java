package com.gingerx.focusservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApproverRequest {
    private Long userId;
    private Long approverId;
    private String status;
}
