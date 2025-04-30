package com.gingerx.focusservice.dto;

import com.gingerx.focusservice.enums.ActiveStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApproverResponse {
    private Long id;
    private Long userId;
    private String userName;
    private Long approverId;
    private String approverName;
    private String status;
    private String vCode;
    private String vCodeTime;
    private String deviceName;

    public ApproverResponse(Long id, Long userId, String userName, Long approverId, String approverName, ActiveStatus status,String deviceName) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.approverId = approverId;
        this.approverName = approverName;
        this.status = status.name();
        this.deviceName = deviceName;
    }
}
