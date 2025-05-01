package com.gingerx.focusservice.facade;

import com.gingerx.focusservice.dto.*;
import com.gingerx.focusservice.dtoMapper.AppDetailDtoMapper;
import com.gingerx.focusservice.dtoMapper.AppDtoMapper;
import com.gingerx.focusservice.entity.App;
import com.gingerx.focusservice.enums.ActiveStatus;
import com.gingerx.focusservice.exception.DataNotFoundException;
import com.gingerx.focusservice.service.AppDetailsService;
import com.gingerx.focusservice.service.AppService;
import com.gingerx.focusservice.service.ApproverService;
import com.gingerx.focusservice.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppFacade {
    private final AppService appService;
    private final AppDetailsService appDetailsService;
    private ApproverService approverService;

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
    public List<AppAndDetailResponse> getAppsByUserIdAndApproverId(ApproverRequest approverRequest){
        log.info("AppFace::getAppsByUserIdAndApproverId():: is called with userId: {} and approverId: {}", approverRequest.getUserId(), approverRequest.getApproverId());
        ApproverResponse approverResponse = approverService.getApproverByUserIdAndApproverId(approverRequest.getUserId(), approverRequest.getApproverId());
        if (approverResponse == null) {
            log.info("AppFace::getAppsByUserIdAndApproverId():: Approver not found for userId: {} and approverId: {}", approverRequest.getUserId(), approverRequest.getApproverId());
            throw new DataNotFoundException("Approver not found");
        }

        if(!ActiveStatus.ACTIVE.equals(ActiveStatus.valueOf(approverResponse.getStatus()))){
            log.info("AppFace::getAppsByUserIdAndApproverId():: Approver is not active for userId: {} and approverId: {}", approverRequest.getUserId(), approverRequest.getApproverId());
            throw new DataNotFoundException("Approver is not active");
        }

        List<App> apps = appService.getAllAppsByUserId(approverRequest.getUserId());
        if (apps.isEmpty()) {
            log.info("AppFace::getAppsByUserIdAndApproverId():: No apps found for userId: {} and approverId: {}", approverRequest.getUserId(), approverRequest.getApproverId());
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
        log.info("AppFace::getAppsByUserIdAndApproverId():: is finished with userId: {} and approverId: {}", approverRequest.getUserId(), approverRequest.getApproverId());
        return appAndDetailResponses;
    }
}
