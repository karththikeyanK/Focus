package com.gingerx.focusservice.dtoMapper;

import com.gingerx.focusservice.dto.ApproverRequest;
import com.gingerx.focusservice.dto.ApproverResponse;
import com.gingerx.focusservice.entity.Approver;
import com.gingerx.focusservice.entity.User;
import com.gingerx.focusservice.enums.ActiveStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ApproverDtoMapper {

    public static Approver mapToEntity(ApproverRequest approverRequest, User user, User approver) {
        return Approver.builder()
                .user(user)
                .approver(approver)
                .status(ActiveStatus.valueOf(approverRequest.getStatus()))
                .vCode(approverRequest.getVCode())
                .vCodeTime(approverRequest.getVCodeTime() != null ? LocalDateTime.parse(approverRequest.getVCodeTime()) : null)
                .deviceName(approverRequest.getDeviceName())
                .build();
    }

    public static ApproverResponse mapToResponse(Approver approver) {
        return ApproverResponse.builder()
                .id(approver.getId())
                .userId(approver.getUser().getId())
                .approverId(approver.getApprover().getId())
                .status(approver.getStatus().name())
                .vCode(approver.getVCode())
                .vCodeTime(approver.getVCodeTime() != null ? approver.getVCodeTime().toString() : null)
                .deviceName(approver.getDeviceName())
                .build();
    }

    public static ApproverRequest mapToRequest(ApproverResponse approverResponse) {
        return ApproverRequest.builder()
                .userId(approverResponse.getUserId())
                .approverId(approverResponse.getApproverId())
                .status(approverResponse.getStatus())
                .vCode(approverResponse.getVCode())
                .vCodeTime(approverResponse.getVCodeTime())
                .deviceName(approverResponse.getDeviceName())
                .build();
    }
}