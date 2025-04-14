package com.gingerx.focusservice.dtoMapper;

import com.gingerx.focusservice.dto.ApprovalRequest;
import com.gingerx.focusservice.dto.ApprovalResponse;
import com.gingerx.focusservice.entity.App;
import com.gingerx.focusservice.entity.Approval;
import com.gingerx.focusservice.entity.Approver;
import com.gingerx.focusservice.entity.User;
import com.gingerx.focusservice.enums.Status;
import com.gingerx.focusservice.util.DurationUtil;
import org.springframework.stereotype.Component;


@Component
public class ApprovalDtoMapper {
    public static Approval mapToEntity(ApprovalRequest approvalRequest, User user, Approver approver, App app) {
        return Approval.builder()
                .scheduledTime(approvalRequest.getScheduledTime())
                .requestDuration(DurationUtil.convertToDuration(approvalRequest.getRequestDuration()))
                .status(Status.valueOf(approvalRequest.getStatus()))
                .user(user)
                .approver(approver)
                .app(app)
                .createdAt(approvalRequest.getCreatedAt())
                .updatedAt(approvalRequest.getUpdatedAt())
                .build();
    }


    public static ApprovalResponse mapToResponse(Approval approval) {
        return ApprovalResponse.builder()
                .id(approval.getId())
                .scheduledTime(approval.getScheduledTime())
                .requestDuration(DurationUtil.formatDuration(approval.getRequestDuration()))
                .status(approval.getStatus().name())
                .userId(approval.getUser().getId())
                .approverId(approval.getApprover().getId())
                .restrictedAppId(approval.getApp().getId())
                .createdAt(approval.getCreatedAt())
                .updatedAt(approval.getUpdatedAt())
                .build();
    }

}