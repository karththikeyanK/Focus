package com.gingerx.focusservice.dtoMapper;

import com.gingerx.focusservice.dto.AppDetailRequest;
import com.gingerx.focusservice.dto.AppDetailResponse;
import com.gingerx.focusservice.entity.AppDetail;
import org.springframework.stereotype.Component;

@Component
public class AppDetailDtoMapper {

    public static AppDetail mapToEntity(AppDetailRequest appDetailRequest) {
        return AppDetail.builder()
                .appName(appDetailRequest.getAppName())
                .appId(appDetailRequest.getAppId())
                .appImageUrl(appDetailRequest.getAppImageUrl())
                .build();
    }

    public static AppDetailResponse mapToResponse(AppDetail appDetail) {
        return AppDetailResponse.builder()
                .id(appDetail.getId())
                .appName(appDetail.getAppName())
                .appId(appDetail.getAppId())
                .appImageUrl(appDetail.getAppImageUrl())
                .build();
    }
}
