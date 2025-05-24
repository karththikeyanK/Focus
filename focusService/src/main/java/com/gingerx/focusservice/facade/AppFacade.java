package com.gingerx.focusservice.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gingerx.focusservice.dto.*;
import com.gingerx.focusservice.dtoMapper.AppDetailDtoMapper;
import com.gingerx.focusservice.dtoMapper.AppDtoMapper;
import com.gingerx.focusservice.entity.App;
import com.gingerx.focusservice.enums.ActiveStatus;
import com.gingerx.focusservice.exception.DataNotFoundException;
import com.gingerx.focusservice.service.AppDetailsService;
import com.gingerx.focusservice.service.AppService;
import com.gingerx.focusservice.service.ApproverService;
import com.gingerx.focusservice.service.UserService;
import com.gingerx.focusservice.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppFacade {
    private final AppService appService;
    private final AppDetailsService appDetailsService;
    private final ApproverService approverService;
    private final UserService userService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    /**
     * This method is used to add apps and their details.
     * It first checks if the app detail already exists.
     * If it does not exist, it creates a new app detail.
     * Then it checks if the app already exists for the user.
     * If it does not exist, it creates a new app.
     */
    @Transactional
    public void addApps(List<AppAndDetailRequest> appAndDetailRequestList) {
       log.info("AppFace::addApps():: is called with size: {}", appAndDetailRequestList.size());
       for (AppAndDetailRequest appAndDetailRequest : appAndDetailRequestList) {
           AppDetailResponse appDetailResponse =appDetailsService.getAppDetailByAppId(appAndDetailRequest.getAppDetailRequest().getAppId());
           if (appDetailResponse == null) {
               log.info("AppFace::addApps():: AppDetail not found, creating new AppDetail for appId: {}", appAndDetailRequest.getAppDetailRequest().getAppId());
               AppDetailRequest appDetailRequest = appAndDetailRequest.getAppDetailRequest();
               String image = appDetailRequest.getAppImage();
               if (image == null || image.isEmpty()) {
                   log.info("AppFace::addApps():: AppImage is null or empty");
                   throw new DataNotFoundException("AppImage is null or empty");
               }
               byte[] appImage = ImageUtil.decodeBase64Image(image);
               String appImageUrl = ImageUtil.saveImage(appImage,
                       appDetailRequest.getAppId());
               log.info("AppFace::addApps():: AppImageUrl: {}", appImageUrl);
               appDetailRequest.setAppImageUrl(appImageUrl);
               appDetailResponse = appDetailsService.createAppDetail(appDetailRequest);
           }
           AppRequest appRequest = appAndDetailRequest.getAppRequest();
           appRequest.setAppDetailId(appDetailResponse.getId());
           appRequest.setStatus("ALLOWED");
           if (appService.existByUserIdAndAppId(appRequest.getUserId(), appRequest.getAppDetailId())) {
               log.info("AppFace::addApps():: App already exists for the user with appDetailId: {}", appRequest.getAppDetailId());
           } else {
               log.info("AppFace::addApps():: Creating new App for user with ppDetailId: {}", appRequest.getAppDetailId());
               appService.create(appRequest);
           }
       }
       log.info("AppFace::addApps():: is finished");
    }


    /**
     * Only Active Approver can see the apps.
     */
    public List<AppAndDetailResponse> getAppsByUserIdAndApproverId(Long userId, Long approverId) {
        log.info("AppFace::getAppsByUserIdAndApproverId():: is called with userId: {} and approverId: {}", userId, approverId);
        ApproverResponse approverResponse = approverService.getApproverByUserIdAndApproverId(userId, approverId);
        if (approverResponse == null) {
            log.info("AppFace::getAppsByUserIdAndApproverId():: Approver not found for userId: {} and approverId: {}", userId, approverId);
            throw new DataNotFoundException("Approver not found");
        }

        if(!ActiveStatus.ACTIVE.equals(ActiveStatus.valueOf(approverResponse.getStatus()))){
            log.info("AppFace::getAppsByUserIdAndApproverId():: Approver is not active for userId: {} and approverId: {}", userId, approverId);
            throw new DataNotFoundException("Approver is not active");
        }

        List<App> apps = appService.getAllAppsByUserId(userId);
        if (apps.isEmpty()) {
            log.info("AppFace::getAppsByUserIdAndApproverId():: No apps found for userId: {} and approverId: {}", userId, approverId);
            throw new DataNotFoundException("No apps found");
        }
        List<AppAndDetailResponse> appAndDetailResponses = new ArrayList<>();
        for (App app : apps) {
            AppAndDetailResponse appAndDetailResponse = AppAndDetailResponse.builder()
                    .appResponse(AppDtoMapper.mapToResponse(app))
                    .appDetailResponse(AppDetailDtoMapper.mapToResponse(app.getAppDetail()))
                    .build();
            appAndDetailResponses.add(appAndDetailResponse);
        }
        log.info("AppFace::getAppsByUserIdAndApproverId():: is finished with userId: {} and approverId: {}", userId, approverId);
        return appAndDetailResponses;
    }


    public List<AppAndDetailResponse> getAppsByUserId(Long userId) {
        log.info("AppFace::getAppsByUserId():: is called with userId: {}", userId);
        List<App> apps = appService.getAllAppsByUserId(userId);
        if (apps.isEmpty()) {
            log.info("AppFace::getAppsByUserId():: No apps found for userId: {}", userId);
            throw new DataNotFoundException("No apps found");
        }
        List<AppAndDetailResponse> appAndDetailResponses = new ArrayList<>();
        for (App app : apps) {
            AppAndDetailResponse appAndDetailResponse = AppAndDetailResponse.builder()
                    .appResponse(AppDtoMapper.mapToResponse(app))
                    .appDetailResponse(AppDetailDtoMapper.mapToResponse(app.getAppDetail()))
                    .build();
            appAndDetailResponses.add(appAndDetailResponse);
        }
        log.info("AppFace::getAppsByUserId():: is finished with userId: {}", userId);
        return appAndDetailResponses;
    }


    public AppResponse updateStatus(Long appId, Long approverId, AppRequest appRequest) {
        log.info("AppFace::updateStatus():: is called with appId: {} and status: {}", appId, appRequest.getStatus());
        AppResponse appResponse = appService.getRestrictedAppById(appId);
        if (appResponse == null) {
            log.info("AppFace::updateStatus():: App not found for appId: {}", appId);
            throw new DataNotFoundException("App not found");
        }

        ApproverResponse approverResponse = approverService.getApproverByUserIdAndApproverId(appResponse.getUserId(), approverId);
        if (approverResponse == null) {
            log.info("AppFace::updateStatus():: Approver not found for userId: {} and approverId: {}", appResponse.getUserId(), approverId);
            throw new DataNotFoundException("Illegal approver!. Approver not found");
        }

        if(!ActiveStatus.ACTIVE.equals(ActiveStatus.valueOf(approverResponse.getStatus()))){
            log.info("AppFace::updateStatus():: Approver is not active for userId: {} and approverId: {}", appResponse.getUserId(), approverId);
            throw new DataNotFoundException("Illegal approver!. Approver is not active");
        }

        AppRequest appRequestToUpdate = AppRequest.builder()
                .userId(appResponse.getUserId())
                .appDetailId(appResponse.getAppDetailId())
                .status(appRequest.getStatus())
                .build();

        AppResponse updatedAppResponse = appService.update(appId, appRequestToUpdate);
        log.info("AppFace::updateStatus():: App status updated successfully for appId: {}", appId);


        NotificationDto notificationDto = new NotificationDto();
        UserResponse userResponse = userService.getUserById(updatedAppResponse.getUserId());
        AppDetailResponse appDetailResponse = appDetailsService.getAppDetailById(updatedAppResponse.getId());
        if (updatedAppResponse.getStatus().equals("ALLOWED")){
            notificationDto.setTitle(appDetailResponse.getAppName()+" Approved");
            notificationDto.setBody(appDetailResponse.getAppName()+" has been approved by your approver");
        }else {
            notificationDto.setTitle(appDetailResponse.getAppName()+" Restricted");
            notificationDto.setBody(appDetailResponse.getAppName()+" has been restricted by your approver");
        }
        notificationDto.setRecipientToken(userResponse.getFirebaseToken());
        notificationDto.setData(Map.of("type", "status-update"));
        try {
            String jsonString = objectMapper.writeValueAsString(notificationDto);
            kafkaTemplate.send("send-push-notification", jsonString);
            log.info("AppFace::updateStatus():: notification is sent to Kafka");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info("AppFace::updateStatus():: notification is sent to user: {}", userResponse.getId());
        return updatedAppResponse;
    }


}
