package com.gingerx.focusservice.service;

import com.gingerx.focusservice.dto.AppRequest;
import com.gingerx.focusservice.dto.AppResponse;
import com.gingerx.focusservice.dtoMapper.AppDtoMapper;
import com.gingerx.focusservice.entity.App;
import com.gingerx.focusservice.entity.AppDetail;
import com.gingerx.focusservice.entity.User;
import com.gingerx.focusservice.exception.ResourceNotFoundException;
import com.gingerx.focusservice.repository.RestrictedAppRepository;
import com.gingerx.focusservice.util.ServiceUtil;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppService {

    private final RestrictedAppRepository restrictedAppRepository;
    private final EntityManager entityManager;
    private final UserService userService;
    private final AppDetailsService appDetailsService;

    public AppResponse create(AppRequest appRequest){
        log.info("AppService::create():: is called with user id: {}", appRequest.getUserId());
        User user = ServiceUtil.validateEntity(
                userService.existById(appRequest.getUserId()),
                () -> entityManager.getReference(User.class, appRequest.getUserId()),
                "User", appRequest.getUserId());

        AppDetail appDetail = ServiceUtil.validateEntity(
                appDetailsService.isExistById(appRequest.getAppDetailId() ),
                () -> entityManager.getReference(AppDetail.class, appRequest.getAppDetailId()),
                "AppDetail", appRequest.getAppDetailId());

        if (restrictedAppRepository.existsByAppDetailIdAndUserId(appRequest.getAppDetailId(), appRequest.getUserId())){
            log.error("AppService::create()::App already exists for the user id: {} ", appRequest.getUserId());
            throw new ResourceNotFoundException("Restricted app already exists for the user");
        }
        App app = restrictedAppRepository.save(AppDtoMapper.mapToEntity(appRequest, user,appDetail));
        log.info("AppService::create():: App created successfully with id: {}", app.getId());
        return AppDtoMapper.mapToResponse(app);
    }

    public AppResponse update(Long id, AppRequest appRequest){
        log.info("AppService::update():: is called with id: {}", id);
        App app = restrictedAppRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("AppService::update():: App not found with id: {}", id);
                    return new ResourceNotFoundException("App not found with id: " + id);
                });
        User user = ServiceUtil.validateEntity(
                userService.existById(appRequest.getUserId()),
                () -> entityManager.getReference(User.class, appRequest.getUserId()),
                "User", appRequest.getUserId());

        AppDetail appDetail = ServiceUtil.validateEntity(
                appDetailsService.isExistById(appRequest.getAppDetailId() ),
                () -> entityManager.getReference(AppDetail.class, appRequest.getAppDetailId()),
                "AppDetail", appRequest.getAppDetailId());
        if (restrictedAppRepository.existsByAppDetailIdAndUserId(appRequest.getAppDetailId(), appRequest.getUserId()) && !appRequest.getAppDetailId().equals(app.getAppDetail().getId())){
            log.error("AppService::update():: App already exists for the user with user id {}", appRequest.getUserId());
            throw new ResourceNotFoundException("Restricted app already exists for the user");
        }
        App updatedApp = AppDtoMapper.mapToEntity(appRequest, user, appDetail);
        updatedApp.setId(app.getId());
        log.info("AppService::update():: App updated successfully with id: {}", app.getId());
        return AppDtoMapper.mapToResponse(restrictedAppRepository.save(updatedApp));
    }

    public List<AppResponse> getAllRestrictedAppsByUserId(Long userId){
        log.info("AppService::getAllRestrictedAppsByUserId():: is called with userId: {}", userId);
        User user = ServiceUtil.validateEntity(
                userService.existById(userId),
                () -> entityManager.getReference(User.class, userId),
                "User", userId);
        List<App> apps = restrictedAppRepository.findAllByUser(user);
        if (apps.isEmpty()){
            log.error("AppService::getAllRestrictedAppsByUserId()::RestrictedApps not found for userId: {}", userId);
            throw new ResourceNotFoundException("RestrictedApps not found for userId: " + userId);
        }
        log.info("AppService::getAllRestrictedAppsByUserId():: RestrictedApps found for userId: {} are: {}", userId, apps.size());
        return apps.stream().map(AppDtoMapper::mapToResponse).toList();
    }

    public AppResponse getRestrictedAppById(Long id) {
        log.info("AppService::getRestrictedAppById():: is called with id: {}", id);
        App app = restrictedAppRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("AppService::getRestrictedAppById():: App not found with id: {}", id);
                    return new ResourceNotFoundException("App not found with id: " + id);
                });
        log.info("AppService::getRestrictedAppById():: App found with id: {}", id);
        return AppDtoMapper.mapToResponse(app);
    }

    public void delete(Long id){
        log.info("AppService::delete():: is called with id: {}", id);
        if (!restrictedAppRepository.existsById(id)){
            log.error("App does not exist with id: {}", id);
            throw new ResourceNotFoundException("App does not exist with id: " + id);
        }
        restrictedAppRepository.deleteById(id);
        log.info("AppService::delete():: App deleted successfully with id: {}", id);
    }

    public boolean existById(Long id){
        log.info("AppService::existById():: is called with id: {}", id);
        boolean isExist = restrictedAppRepository.existsById(id);
        log.info("AppService::existById():: isExist with id: {} is: {}", id, isExist);
        return isExist;
    }

    public boolean existByUserIdAndAppId(Long userId, Long appDetailId){
        log.info("AppService::existByUserIdAndAppId():: is called with userId: {} and appDetailId: {}", userId, appDetailId);
        boolean isExist = restrictedAppRepository.existsByUserIdAndAppDetailId(userId, appDetailId);
        log.info("AppService::existByUserIdAndAppId():: isExist with userId: {} and appDetailId: {} is: {}", userId, appDetailId, isExist);
        return isExist;
    }

    public List<App> getAllAppsByUserId(Long userId){
        log.info("AppService::getAllAppsByUserId():: is called with userId: {}", userId);
        User user = ServiceUtil.validateEntity(
                userService.existById(userId),
                () -> entityManager.getReference(User.class, userId),
                "User", userId);
        List<App> apps = restrictedAppRepository.findAllByUser(user);
        if (apps.isEmpty()){
            log.error("AppService::getAllAppsByUserId():: Apps not found for userId: {}", userId);
            throw new ResourceNotFoundException("Apps not found for userId: " + userId);
        }
        log.info("AppService::getAllAppsByUserId():: Apps found for userId: {} are: {}", userId, apps.size());
        return apps;
    }
}