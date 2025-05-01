package com.gingerx.focusservice.service;

import com.gingerx.focusservice.dto.ApproverRequest;
import com.gingerx.focusservice.dto.ApproverResponse;
import com.gingerx.focusservice.dtoMapper.ApproverDtoMapper;
import com.gingerx.focusservice.entity.Approver;
import com.gingerx.focusservice.entity.User;
import com.gingerx.focusservice.exception.DataNotFoundException;
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
        Approver updatedApprover = ApproverDtoMapper.mapToEntity(approverRequest, user, approverUser);
        updatedApprover.setId(id);
        updatedApprover = approverRepository.save(updatedApprover);
        log.info("ApproverService::update:: is end with user id: {}", approverRequest.getUserId());
        return ApproverDtoMapper.mapToResponse(updatedApprover);
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

    public List<ApproverResponse> getApproverRequestByApproverId(Long approverId){
        log.info("ApproverService::getApproverListByApproverId:: is start with approver id: {}", approverId);
        User user = ServiceUtil.validateEntity(
                userService.existById(approverId),
                () -> entityManager.getReference(User.class, approverId),
                "User",
                approverId
        );
        List<ApproverResponse> approverList = approverRepository.findAllApproversByApprover(user);
        if (approverList.isEmpty()){
            log.error("ApproverService::getApproverListByApproverId:: Approver list is empty");
            throw new DuplicationException("Approver list is empty");
        }
        log.info("ApproverService::getApproverListByApproverId:: is end with approver id: {}", approverId);
        return approverList;
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

    public ApproverResponse getApproverById(Long id){
        log.info("ApproverService::getApproverById:: is start with id: {}", id);
        Approver approver = approverRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Approver not found with id: "+id));
        log.info("ApproverService::getApproverById:: is end with id: {}", id);
        return ApproverDtoMapper.mapToResponse(approver);
    }

    public ApproverResponse getApproverByApproverId(Long userId) {
        log.info("ApproverService::getApproverByApproverId:: is start with userId: {}", userId);
        Approver approver = approverRepository.findByApproverUserId(userId).orElseThrow(() -> new DataNotFoundException("Approver not found with approver id: "+userId));
        log.info("ApproverService::getApproverByApproverId:: is end with userId: {}", userId);
        return ApproverDtoMapper.mapToResponse(approver);
    }

    public ApproverResponse getApproverByUserIdAndApproverMail(Long id, String mail) {
        log.info("ApproverService::getApproverByMail:: is start with mail: {}", mail);
        Approver approver = approverRepository.findByUser_IdAndApprover_Email(id, mail).orElse(null);
        if (approver == null) {
            log.info("ApproverService::getApproverByMail:: approver not found for mail: {}", mail);
            return null;
        }
        log.info("ApproverService::getApproverByMail:: is end with mail: {}", mail);
        return ApproverDtoMapper.mapToResponse(approver);
    }

    public ApproverResponse getApproverByUserIdAndApproverId(Long userId, Long approverId) {
        log.info("ApproverService::getApproverByUserIdAndApproverId:: is start with userId: {} and approverId: {}", userId, approverId);
        Approver approver = approverRepository.findByUser_IdAndApprover_Id(userId, approverId).orElse(null);
        if (approver == null) {
            log.info("ApproverService::getApproverByUserIdAndApproverId:: approver not found for userId: {} and approverId: {}", userId, approverId);
            return null;
        }
        log.info("ApproverService::getApproverByUserIdAndApproverId:: is end with userId: {} and approverId: {}", userId, approverId);
        return ApproverDtoMapper.mapToResponse(approver);
    }


}
