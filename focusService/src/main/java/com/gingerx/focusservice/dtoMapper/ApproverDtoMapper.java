package com.gingerx.focusservice.dtoMapper;

import com.gingerx.focusservice.dto.ApproverRequest;
import com.gingerx.focusservice.dto.ApproverResponse;
import com.gingerx.focusservice.entity.Approver;
import com.gingerx.focusservice.entity.User;
import com.gingerx.focusservice.enums.ActiveStatus;

public class ApproverDtoMapper {

    public static Approver mapToEntity(ApproverRequest approverRequest, User user, User approver) {
        return Approver.builder()
                .user(user)
                .approver(approver)
                .status(ActiveStatus.valueOf(approverRequest.getStatus()))
                .build();
    }

    public static ApproverResponse mapToResponse(Approver approver) {
        return ApproverResponse.builder()
                .id(approver.getId())
                .userId(approver.getUser().getId())
                .approverId(approver.getApprover().getId())
                .status(approver.getStatus().name())
                .build();
    }
}