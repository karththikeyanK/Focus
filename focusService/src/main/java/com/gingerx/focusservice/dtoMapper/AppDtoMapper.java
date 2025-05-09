package com.gingerx.focusservice.dtoMapper;

import com.gingerx.focusservice.dto.AppAndDetailResponse;
import com.gingerx.focusservice.dto.AppRequest;
import com.gingerx.focusservice.dto.AppResponse;
import com.gingerx.focusservice.entity.App;
import com.gingerx.focusservice.entity.AppDetail;
import com.gingerx.focusservice.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AppDtoMapper {

    public static App mapToEntity(AppRequest appRequest, User user, AppDetail appDetail) {
        return App.builder()
                .user(user)
                .appDetail(appDetail)
                .status(appRequest.getStatus())
                .build();
    }

    public static AppResponse mapToResponse(App app) {
        return AppResponse.builder()
                .id(app.getId())
                .appDetailId(app.getAppDetail().getId())
                .status(app.getStatus())
                .userId(app.getUser().getId())
                .build();
    }



}
