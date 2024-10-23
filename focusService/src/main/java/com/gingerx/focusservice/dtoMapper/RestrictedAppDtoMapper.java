package com.gingerx.focusservice.dtoMapper;

import com.gingerx.focusservice.dto.RestrictedAppRequest;
import com.gingerx.focusservice.dto.RestrictedAppResponse;
import com.gingerx.focusservice.entity.RestrictedApp;
import com.gingerx.focusservice.entity.User;
import org.springframework.stereotype.Component;

@Component
public class RestrictedAppDtoMapper {

    public static RestrictedApp mapToEntity(RestrictedAppRequest restrictedAppRequest, User user) {
        return RestrictedApp.builder()
                .appName(restrictedAppRequest.getAppName())
                .user(user)
                .build();
    }

    public static RestrictedAppResponse mapToResponse(RestrictedApp restrictedApp) {
        return RestrictedAppResponse.builder()
                .id(restrictedApp.getId())
                .appName(restrictedApp.getAppName())
                .userId(restrictedApp.getUser().getId())
                .build();
    }
}
