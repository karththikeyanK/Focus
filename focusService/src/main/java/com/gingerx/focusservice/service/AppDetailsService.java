package com.gingerx.focusservice.service;


import com.gingerx.focusservice.dto.AppDetailRequest;
import com.gingerx.focusservice.dto.AppDetailResponse;
import com.gingerx.focusservice.dtoMapper.AppDetailDtoMapper;
import com.gingerx.focusservice.entity.AppDetail;
import com.gingerx.focusservice.exception.DataNotFoundException;
import com.gingerx.focusservice.exception.DuplicationException;
import com.gingerx.focusservice.repository.AppDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
@Slf4j
public class AppDetailsService {

    private final AppDetailRepository appDetailRepository;
    public AppDetailResponse createAppDetail(AppDetailRequest appDetailRequest) {
        log.info("AppDetailsService::createAppDetail():: is called with appName: {}", appDetailRequest.getAppName());
        if (appDetailRepository.existsByAppId(appDetailRequest.getAppId())) {
            log.error("AppDetailsService::createAppDetail():: AppDetail already exists with appName: {}", appDetailRequest.getAppName());
            throw new DuplicationException("AppDetail already exists with appName: " + appDetailRequest.getAppName());
        }
        AppDetail appDetail = AppDetailDtoMapper.mapToEntity(appDetailRequest);
        log.info("AppDetailsService::createAppDetail():: AppDetail created successfully with name: {}", appDetail.getAppName());
        return AppDetailDtoMapper.mapToResponse(appDetailRepository.save(appDetail));
    }


    public AppDetailResponse getAppDetailById(String appId) {
        log.info("AppDetailsService::getAppDetail():: is called with appId: {}", appId);
        AppDetail appDetail = appDetailRepository.findByAppId(appId)
                .orElseThrow(() -> new DataNotFoundException("AppDetail not found with appId: " + appId));
        log.info("AppDetailsService::getAppDetail():: AppDetail retrieved successfully with name: {}", appDetail.getAppName());
        return AppDetailDtoMapper.mapToResponse(appDetail);
    }

    public AppDetailResponse getAppDetailByAppId(String appId) {
        log.info("AppDetailsService::getAppDetail():: is called with id: {}", appId);
        AppDetail appDetail = appDetailRepository.findByAppId(appId)
                .orElse(null);
        log.info("AppDetailsService::getAppDetailByAppId()::AppDetail retrieved successfully with name: {}", appDetail.getAppName());
        return AppDetailDtoMapper.mapToResponse(appDetail);
    }

    public AppDetailResponse updateAppDetail(Long id, AppDetailRequest appDetailRequest) {
        log.info("AppDetailsService::updateAppDetail():: is called with id: {}", id);
        AppDetail appDetail = appDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("AppDetail not found with Id: " + id));
        if (appDetailRepository.existsByAppId(appDetailRequest.getAppId()) && !appDetailRequest.getAppId().equals(appDetail.getAppId())) {
            log.error("AppDetailsService::updateAppDetail():: AppDetail already exists with appName: {}", appDetailRequest.getAppName());
            throw new DuplicationException("AppDetail already exists with appName: " + appDetailRequest.getAppName());
        }

        appDetail = AppDetailDtoMapper.mapToEntity(appDetailRequest);
        appDetail.setId(appDetail.getId());
        log.info("AppDetail updated successfully with name: {}", appDetail.getAppName());
        return AppDetailDtoMapper.mapToResponse(appDetailRepository.save(appDetail));
    }

    public String deleteAppDetail(Long id) {
        log.info("AppDetailsService::deleteAppDetail():: is called with id: {}", id);
        AppDetail appDetail = appDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("AppDetail not found with Id: " + id));
        appDetailRepository.delete(appDetail);
        log.info("AppDetailsService::deleteAppDetail()::AppDetail deleted successfully with name: {}", appDetail.getAppName());
        return "AppDetail deleted successfully with name: " + appDetail.getAppName();
    }

    public boolean isExistById(Long id) {
        log.info("AppDetailsService::isExistById():: is called with id: {}", id);
        boolean exists = appDetailRepository.existsById(id);
        log.info("AppDetailsService::isExistById():: AppDetail exists: {}", exists);
        return exists;
    }

    public boolean isExistByAppId(String appId) {
        log.info("AppDetailsService::isExistByAppId():: is called with appId: {}", appId);
        boolean exists = appDetailRepository.existsByAppId(appId);
        log.info("AppDetailsService::isExistByAppId():: AppDetail exists: {}", exists);
        return exists;
    }
}
