package com.gingerx.focusservice.service;

import com.gingerx.focusservice.dto.RestrictedAppRequest;
import com.gingerx.focusservice.dto.RestrictedAppResponse;
import com.gingerx.focusservice.dtoMapper.RestrictedAppDtoMapper;
import com.gingerx.focusservice.entity.RestrictedApp;
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
public class RestrictedAppService {

    private final RestrictedAppRepository restrictedAppRepository;
    private final EntityManager entityManager;
    private final UserService userService;

    public RestrictedAppResponse create(RestrictedAppRequest restrictedAppRequest){
        log.info("RestrictedAppService::create():: is called with appName: {}", restrictedAppRequest.getAppName());
        User user = ServiceUtil.validateEntity(
                userService.existById(restrictedAppRequest.getUserId()),
                () -> entityManager.getReference(User.class, restrictedAppRequest.getUserId()),
                "User",restrictedAppRequest.getUserId());

        if (restrictedAppRepository.existsByAppNameAndUser(restrictedAppRequest.getAppName(), user)){
            log.error("RestrictedAppService::create()::Restricted app already exists for the user with appName: {}", restrictedAppRequest.getAppName());
            throw new ResourceNotFoundException("Restricted app already exists for the user");
        }
        RestrictedApp restrictedApp = restrictedAppRepository.save(RestrictedAppDtoMapper.mapToEntity(restrictedAppRequest, user));
        log.info("RestrictedAppService::create():: RestrictedApp created successfully with name: {}", restrictedApp.getAppName());
        return RestrictedAppDtoMapper.mapToResponse(restrictedApp);
    }

    public RestrictedAppResponse update(Long id, RestrictedAppRequest restrictedAppRequest){
        log.info("RestrictedAppService::update():: is called with id: {}", id);
        RestrictedApp restrictedApp = restrictedAppRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("RestrictedAppService::update():: RestrictedApp not found with id: {}", id);
                    return new ResourceNotFoundException("RestrictedApp not found with id: " + id);
                });
        User user = ServiceUtil.validateEntity(
                userService.existById(restrictedAppRequest.getUserId()),
                () -> entityManager.getReference(User.class, restrictedAppRequest.getUserId()),
                "User",restrictedAppRequest.getUserId());

        if (restrictedAppRepository.existsByAppNameAndUser(restrictedAppRequest.getAppName(), user) && !restrictedApp.getAppName().equals(restrictedAppRequest.getAppName())){
            log.error("RestrictedAppService::update()::Restricted app already exists for the user with appName: {}", restrictedAppRequest.getAppName());
            throw new ResourceNotFoundException("Restricted app already exists for the user");
        }
        RestrictedApp updatedRestrictedApp = RestrictedAppDtoMapper.mapToEntity(restrictedAppRequest, user);
        updatedRestrictedApp.setId(id);
        updatedRestrictedApp = restrictedAppRepository.save(updatedRestrictedApp);
        log.info("RestrictedAppService::update():: RestrictedApp updated successfully with id: {}", id);
        return RestrictedAppDtoMapper.mapToResponse(updatedRestrictedApp);
    }

    public List<RestrictedAppResponse> getAllRestrictedAppsByUserId(Long userId){
        log.info("RestrictedAppService::getAllRestrictedAppsByUserId():: is called with userId: {}", userId);
        User user = ServiceUtil.validateEntity(
                userService.existById(userId),
                () -> entityManager.getReference(User.class, userId),
                "User", userId);
        List<RestrictedApp> restrictedApps = restrictedAppRepository.findAllByUser(user);
        if (restrictedApps.isEmpty()){
            log.error("RestrictedAppService::getAllRestrictedAppsByUserId()::RestrictedApps not found for userId: {}", userId);
            throw new ResourceNotFoundException("RestrictedApps not found for userId: " + userId);
        }
        log.info("RestrictedAppService::getAllRestrictedAppsByUserId():: RestrictedApps found for userId: {} are: {}", userId, restrictedApps.size());
        return restrictedApps.stream().map(RestrictedAppDtoMapper::mapToResponse).toList();
    }

    public RestrictedAppResponse getRestrictedAppById(Long id) {
        log.info("RestrictedAppService::getRestrictedAppById():: is called with id: {}", id);
        RestrictedApp restrictedApp = restrictedAppRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("RestrictedAppService::getRestrictedAppById():: RestrictedApp not found with id: {}", id);
                    return new ResourceNotFoundException("RestrictedApp not found with id: " + id);
                });
        log.info("RestrictedAppService::getRestrictedAppById():: RestrictedApp found with id: {}", id);
        return RestrictedAppDtoMapper.mapToResponse(restrictedApp);
    }

    public void delete(Long id){
        log.info("RestrictedAppService::delete():: is called with id: {}", id);
        if (!restrictedAppRepository.existsById(id)){
            log.error("RestrictedApp does not exist with id: {}", id);
            throw new ResourceNotFoundException("RestrictedApp does not exist with id: " + id);
        }
        restrictedAppRepository.deleteById(id);
        log.info("RestrictedAppService::delete():: RestrictedApp deleted successfully with id: {}", id);
    }

    public boolean existById(Long id){
        log.info("RestrictedAppService::existById():: is called with id: {}", id);
        boolean isExist = restrictedAppRepository.existsById(id);
        log.info("RestrictedAppService::existById():: isExist with id: {} is: {}", id, isExist);
        return isExist;
    }
}