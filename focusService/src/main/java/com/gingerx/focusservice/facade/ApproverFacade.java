package com.gingerx.focusservice.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gingerx.focusservice.dto.*;
import com.gingerx.focusservice.dtoMapper.ApproverDtoMapper;
import com.gingerx.focusservice.enums.ActiveStatus;
import com.gingerx.focusservice.exception.DuplicationException;
import com.gingerx.focusservice.exception.VerificationException;
import com.gingerx.focusservice.service.ApproverService;
import com.gingerx.focusservice.service.UserService;
import com.gingerx.focusservice.util.OtpGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;


@Service
@Slf4j
@RequiredArgsConstructor
public class ApproverFacade {

    private final ApproverService approverService;
    private final UserService userService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final AppFacade appFacade;

    public ApproverResponse addApprover(Long id, ApproverDto approverDto) {
        log.info("AppFace::addApprover():: is called");
        String mail = approverDto.getEmail();
        UserResponse user = userService.getUserById(id);
        UserResponse approver = userService.getUserByEmail(mail);

        if(user.getEmail().equals(mail)) {
            log.error("AppFace::addApprover():: User cannot be approver");
            throw new DuplicationException("User cannot be approver");
        }
        String vCode = OtpGenerator.generateOtp();

        ApproverResponse approverResponse;
        String title, body;
        ApproverResponse existingApprover = approverService.getApproverByUserIdAndApproverMail(user.getId(), mail);
        if(existingApprover != null && existingApprover.getStatus().equals(ActiveStatus.PENDING.name())) {
            log.warn("AppFace::addApprover():: Approver already exists & device name is {}", approverDto.getDeviceName());
            existingApprover.setVCode(vCode);
            existingApprover.setVCodeTime(LocalDateTime.now().plusMinutes(5).toString());
            existingApprover.setDeviceName(approverDto.getDeviceName());
            approverResponse = approverService.update(existingApprover.getId(), ApproverDtoMapper.mapToRequest(existingApprover));
            title = "Approve Request updated by " + user.getFirstName();
            body = user.getFirstName() + " has updated the Approve Request for you. Please contact him for the verification code. or cancel it.";
            log.info("AppFace::addApprover():: Approver is updated");
            appFacade.addApps(approverDto.getApps());
            log.info("AppFace::addApprover():: Apps are updated");
        }else if(existingApprover == null){
            log.info("AppFace::addApprover():: Approver is started to create");
            ApproverRequest approverRequest = new ApproverRequest();
            approverRequest.setUserId(user.getId());
            approverRequest.setApproverId(approver.getId());
            approverRequest.setVCode(vCode);
            approverRequest.setVCodeTime(LocalDateTime.now().plusMinutes(5).toString());
            approverRequest.setStatus(ActiveStatus.PENDING.name());
            approverRequest.setDeviceName(approverDto.getDeviceName());
            approverResponse = approverService.create(approverRequest);
            title = "Approve Request by " + user.getFirstName();
            body = user.getFirstName() + " has sent you an Approve Request. Please contact him for the verification code.";
            log.info("AppFace::addApprover():: Approver is created");
            appFacade.addApps(approverDto.getApps());
            log.info("AppFace::addApprover():: Apps are added");
        }else {
            log.error("AppFace::addApprover():: Approver already exists for this user");
            throw new DuplicationException("Approver already exists for this user");
        }

        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setTitle(title);
        notificationDto.setBody(body);
        notificationDto.setRecipientToken(approver.getFirebaseToken());
        notificationDto.setData(Map.of("approverId", approverResponse.getId().toString()));

        try {
            String jsonString = objectMapper.writeValueAsString(notificationDto);
            kafkaTemplate.send("send-push-notification", jsonString);
            log.info("AppFace::addApprover():: Approver is sent to Kafka");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        log.info("AppFace::addApprover():: is finished");
        return approverResponse;
    }


    public ApproverResponse confirmApprover(Long id, String vCode) {
        log.info("AppFace::confirmApprover():: is called");
        ApproverResponse approverResponse = approverService.getApproverById(id);
        if(approverResponse.getVCode().equals(vCode) &&
                approverResponse.getVCodeTime() != null &&
                LocalDateTime.parse(approverResponse.getVCodeTime()).isAfter(LocalDateTime.now())) {
            approverResponse.setStatus(ActiveStatus.ACTIVE.name());
            approverResponse.setVCode(null);
            approverResponse.setVCodeTime(null);
            approverResponse = approverService.update(id, ApproverDtoMapper.mapToRequest(approverResponse));
        } else {
            log.error("AppFace::confirmApprover():: VCode is not valid");
            throw new VerificationException("VCode is not valid");
        }
        log.info("AppFace::confirmApprover():: is finished");
        return approverResponse;
    }
}
