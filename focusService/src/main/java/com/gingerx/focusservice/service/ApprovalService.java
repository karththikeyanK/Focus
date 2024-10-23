package com.gingerx.focusservice.service;

import com.gingerx.focusservice.dto.ApprovalRequest;
import com.gingerx.focusservice.dto.ApprovalResponse;
import com.gingerx.focusservice.dto.filter.ApprovalFilter;
import com.gingerx.focusservice.dtoMapper.ApprovalDtoMapper;
import com.gingerx.focusservice.entity.Approval;
import com.gingerx.focusservice.entity.Approver;
import com.gingerx.focusservice.entity.RestrictedApp;
import com.gingerx.focusservice.entity.User;
import com.gingerx.focusservice.enums.Status;
import com.gingerx.focusservice.exception.DuplicationException;
import com.gingerx.focusservice.exception.ResourceNotFoundException;
import com.gingerx.focusservice.repository.ApprovalRepository;
import com.gingerx.focusservice.util.DurationUtil;
import com.gingerx.focusservice.util.ServiceUtil;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApprovalService {
    private final ApprovalRepository approvalRepository;
    private final EntityManager entityManager;
    private final UserService userService;
    private final RestrictedAppService restrictedAppService;
    private final ApproverService approverService;

    public ApprovalResponse create(ApprovalRequest approvalRequest){
        log.info("ApprovalService::create():: is called");
        User user = ServiceUtil.validateEntity(
                userService.existById(approvalRequest.getUserId()),
                () -> entityManager.getReference(User.class, approvalRequest.getUserId()),
                "User", approvalRequest.getUserId()
        );

        RestrictedApp restrictedApp = ServiceUtil.validateEntity(
                restrictedAppService.existById(approvalRequest.getRestrictedAppId()),
                () -> entityManager.getReference(RestrictedApp.class, approvalRequest.getRestrictedAppId()),
                "RestrictedApp", approvalRequest.getRestrictedAppId()
        );

        Approver approver = ServiceUtil.validateEntity(
                approverService.existById(approvalRequest.getApproverId()),
                () -> entityManager.getReference(Approver.class, approvalRequest.getApproverId()),
                "Approver", approvalRequest.getApproverId()
        );
        LocalDateTime scheduledEndTime = approvalRequest.getScheduledTime().plus(DurationUtil.convertToDuration(approvalRequest.getRequestDuration()));
        if (!approvalRepository.findConflictingApprovalsByUserAndApp(restrictedApp, user,approvalRequest.getScheduledTime(),scheduledEndTime).isEmpty()){
            log.error("ApprovalService::create()::Approval already exists for the user");
            throw new DuplicationException("Approval already exists for the user on this time");
        }
        Approval approval = approvalRepository.save(ApprovalDtoMapper.mapToEntity(approvalRequest, user, approver, restrictedApp));
        log.info("ApprovalService::create()::Approval created successfully");
        return ApprovalDtoMapper.mapToResponse(approval);
    }

    public ApprovalResponse update(Long id, ApprovalRequest approvalRequest){
        log.info("ApprovalService::update():: is called with id: {}", id);
        Approval approval = approvalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Approval not found with id: " + id));

        User user = ServiceUtil.validateEntity(
                userService.existById(approvalRequest.getUserId()),
                () -> entityManager.getReference(User.class, approvalRequest.getUserId()),
                "User", approvalRequest.getUserId()
        );

        RestrictedApp restrictedApp = ServiceUtil.validateEntity(
                restrictedAppService.existById(approvalRequest.getRestrictedAppId()),
                () -> entityManager.getReference(RestrictedApp.class, approvalRequest.getRestrictedAppId()),
                "RestrictedApp", approvalRequest.getRestrictedAppId()
        );

        Approver approver = ServiceUtil.validateEntity(
                approverService.existById(approvalRequest.getApproverId()),
                () -> entityManager.getReference(Approver.class, approvalRequest.getApproverId()),
                "Approver", approvalRequest.getApproverId()
        );
        LocalDateTime scheduledEndTime = approvalRequest.getScheduledTime().plus(DurationUtil.convertToDuration(approvalRequest.getRequestDuration()));
        if (!approvalRepository.findConflictingApprovalsByUserAndAppAndId(restrictedApp, user,approvalRequest.getScheduledTime(),scheduledEndTime,id).isEmpty()){
            log.error("ApprovalService::update()::Approval already exists for the user");
            throw new DuplicationException("Approval already exists for the user on this time");
        }
        Approval updatedApproval = ApprovalDtoMapper.mapToEntity(approvalRequest, user, approver, restrictedApp);
        updatedApproval.setId(id);
        updatedApproval = approvalRepository.save(updatedApproval);
        log.info("ApprovalService::update()::Approval updated successfully with id: {}", id);
        return ApprovalDtoMapper.mapToResponse(updatedApproval);
    }

    public ApprovalResponse getById(Long id) {
        log.info("ApprovalService::getById():: is called with id: {}", id);
        Approval approval = approvalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Approval not found with id: " + id));
        log.info("ApprovalService::getById()::Approval fetched successfully with id: {}", id);
        return ApprovalDtoMapper.mapToResponse(approval);
    }

    public List<ApprovalResponse> getByUserId(ApprovalFilter approvalFilter) {
        log.info("ApprovalService::getByUserId():: is called with userId: {}", approvalFilter.getUserId());
        User user = ServiceUtil.validateEntity(
                userService.existById(approvalFilter.getUserId()),
                () -> entityManager.getReference(User.class, approvalFilter.getUserId()),
                "User", approvalFilter.getUserId()
        );
        Pageable pageable = PageRequest.of(approvalFilter.getPageNumber(), approvalFilter.getPageSize());
        List<Approval> approvals = approvalRepository.findByUserOrderByCreatedAtDesc(user, pageable);
        if (approvals.isEmpty()){
            log.error("ApprovalService::getByUserId()::Approvals not found for userId: {}", approvalFilter.getUserId());
            throw new ResourceNotFoundException("Approvals not found for userId: " + approvalFilter.getUserId());
        }
        log.info("ApprovalService::getByUserId()::Approvals fetched successfully with userId: {}", approvalFilter.getUserId());
        return approvals.stream().map(ApprovalDtoMapper::mapToResponse).toList();
    }

    public List<ApprovalResponse> getByUserAndStatus(ApprovalFilter approvalFilter) {
        log.info("ApprovalService::getByUserAndStatus():: is called with userId: {}", approvalFilter.getUserId());
        User user = ServiceUtil.validateEntity(
                userService.existById(approvalFilter.getUserId()),
                () -> entityManager.getReference(User.class, approvalFilter.getUserId()),
                "User", approvalFilter.getUserId()
        );
        Pageable pageable = PageRequest.of(approvalFilter.getPageNumber(), approvalFilter.getPageSize());
        List<Approval> approvals = approvalRepository.findByUserAndStatusOrderByCreatedAtDesc(user, Status.valueOf(approvalFilter.getStatus()), pageable);
        if (approvals.isEmpty()){
            log.error("ApprovalService::getByUserAndStatus()::Approvals not found for userId: {}", approvalFilter.getUserId());
            throw new ResourceNotFoundException("Approvals not found for userId: " + approvalFilter.getUserId());
        }
        log.info("ApprovalService::getByUserAndStatus()::Approvals fetched successfully with userId: {}", approvalFilter.getUserId());
        return approvals.stream().map(ApprovalDtoMapper::mapToResponse).toList();
    }

    public List<ApprovalResponse> getAllByUserAndDateRange(ApprovalFilter approvalFilter) {
        log.info("ApprovalService::getAllByUserAndDateRange():: is called with userId: {}", approvalFilter.getUserId());
        User user = ServiceUtil.validateEntity(
                userService.existById(approvalFilter.getUserId()),
                () -> entityManager.getReference(User.class, approvalFilter.getUserId()),
                "User", approvalFilter.getUserId()
        );
        Pageable pageable = PageRequest.of(approvalFilter.getPageNumber(), approvalFilter.getPageSize());
        List<Approval> approvals = approvalRepository.findByUserAndScheduledTimeBetweenOrderByScheduledTimeDesc(user, approvalFilter.getStartDate(), approvalFilter.getEndDate(), pageable);
        if (approvals.isEmpty()){
            log.error("ApprovalService::getAllByUserAndDateRange()::Approvals not found for userId: {}", approvalFilter.getUserId());
            throw new ResourceNotFoundException("Approvals not found for userId: " + approvalFilter.getUserId());
        }
        log.info("ApprovalService::getAllByUserAndDateRange()::Approvals fetched successfully with userId: {}", approvalFilter.getUserId());
        return approvals.stream().map(ApprovalDtoMapper::mapToResponse).toList();
    }

    public void delete(Long id) {
        log.info("ApprovalService::delete():: is called with id: {}", id);
        Approval approval = approvalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Approval not found with id: " + id));
        approvalRepository.delete(approval);
        log.info("ApprovalService::delete()::Approval deleted successfully with id: {}", id);
    }

}