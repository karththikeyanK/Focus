package com.gingerx.focusservice.dtoMapper;

import com.gingerx.focusservice.dto.AppRequest;
import com.gingerx.focusservice.dto.AppResponse;
import com.gingerx.focusservice.entity.App;
import com.gingerx.focusservice.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AppDtoMapper {

    public static App mapToEntity(AppRequest appRequest, User user) {
        return App.builder()
                .appName(appRequest.getAppName())
                .user(user)
                .appId(appRequest.getAppId())
                .status(appRequest.getStatus())
                .build();
    }

    public static AppResponse mapToResponse(App app) {
        return AppResponse.builder()
                .id(app.getId())
                .appName(app.getAppName())
                .appId(app.getAppId())
                .status(app.getStatus())
                .userId(app.getUser().getId())
                .build();
    }
}
