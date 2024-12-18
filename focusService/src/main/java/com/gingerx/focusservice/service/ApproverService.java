package com.gingerx.focusservice.service;

import com.gingerx.focusservice.dto.ApproverRequest;
import com.gingerx.focusservice.dto.ApproverResponse;
import com.gingerx.focusservice.dtoMapper.ApproverDtoMapper;
import com.gingerx.focusservice.entity.Approver;
import com.gingerx.focusservice.entity.User;
import com.gingerx.focusservice.exception.DuplicationException;
import com.gingerx.focusservice.repository.ApproverRepository;
import com.gingerx.focusservice.util.ServiceUtil;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApproverService {
    private final ApproverRepository approverRepository;
    private final EntityManager entityManager;
    private final UserService userService;

    public ApproverResponse create(ApproverRequest approverRequest){
        log.info("ApproverService::create:: is start with user id: {}", approverRequest.getUserId());
        if(approverRepository.existsByUserIdAndApproverId(approverRequest.getUserId(), approverRequest.getApproverId())){
            log.error("ApproverService::create:: Approver already exists");
            throw new DuplicationException("Approver already exists");
        }
        User user = ServiceUtil.validateEntity(
                userService.existById(approverRequest.getUserId()),
                () -> entityManager.getReference(User.class, approverRequest.getUserId()),
                "User",
                approverRequest.getUserId()
        );
        User approverUser = ServiceUtil.validateEntity(
                userService.existById(approverRequest.getApproverId()),
                () -> entityManager.getReference(User.class, approverRequest.getApproverId()),
                "Approver",
                approverRequest.getApproverId()
        );
        Approver approver = approverRepository.save(ApproverDtoMapper.mapToEntity(approverRequest, user, approverUser));
        log.info("ApproverService::create:: is end with user id: {}", approverRequest.getUserId());
        return ApproverDtoMapper.mapToResponse(approver);
    }

    public ApproverResponse update(Long id,ApproverRequest approverRequest) {
        log.info("ApproverService::update:: is start with user id: {}", approverRequest.getUserId());
        Approver approver = approverRepository.findById(id)
                .orElseThrow(() -> new DuplicationException("Approver not found with id: "+id));

        User user = ServiceUtil.validateEntity(
                userService.existById(approverRequest.getUserId()),
                () -> entityManager.getReference(User.class, approverRequest.getUserId()),
                "User",
                approverRequest.getUserId()
        );
        User approverUser = ServiceUtil.validateEntity(
                userService.existById(approverRequest.getApproverId()),
                () -> entityManager.getReference(User.class, approverRequest.getApproverId()),
                "Approver",
                approverRequest.getApproverId()
        );
        if (!approverRequest.getApproverId().equals(approver.getApprover().getId()) && approverRepository.existsByUserIdAndApproverId(approverRequest.getUserId(), approverRequest.getApproverId())){
            log.error("ApproverService::update:: Approver already exists");
            throw new DuplicationException("Approver already exists");
        }
        approverRepository.save(approver);
        log.info("ApproverService::update:: is end with user id: {}", approverRequest.getUserId());
        return ApproverDtoMapper.mapToResponse(approver);
    }

    public List<ApproverResponse> getApproverList(Long userId){
        log.info("ApproverService::getApproverList:: is start with user id: {}", userId);
        User user = ServiceUtil.validateEntity(
                userService.existById(userId),
                () -> entityManager.getReference(User.class, userId),
                "User",
                userId
        );
        List<Approver> approverList = approverRepository.findByUser(user);
        if (approverList.isEmpty()){
            log.error("ApproverService::getApproverList:: Approver list is empty");
            throw new DuplicationException("Approver list is empty");
        }
        log.info("ApproverService::getApproverList:: is end with user id: {}", userId);
        return approverList.stream().map(ApproverDtoMapper::mapToResponse).toList();
    }

    public void delete(Long approverId){
        log.info("ApproverService::delete:: is start with approver id: {}", approverId);
        if (!approverRepository.existsById(approverId)){
            log.error("ApproverService::delete:: Approver not found");
            throw new DuplicationException("Approver not found");
        }
        approverRepository.deleteById(approverId);
        log.info("ApproverService::delete:: is end with approver id: {}", approverId);
    }

    public boolean existById(Long id){
        log.info("ApproverService::existById:: is start with id: {}", id);
        boolean isExist = approverRepository.existsById(id);
        log.info("ApproverService::existById:: is end with id: {}", id);
        return isExist;
    }

}
