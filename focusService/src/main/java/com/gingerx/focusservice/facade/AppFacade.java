package com.gingerx.focusservice.facade;

import com.gingerx.focusservice.dto.AppRequest;
import com.gingerx.focusservice.service.AppService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppFacade {
    private final AppService appService;

    public void addApps(List<AppRequest> appRequests) {
       log.info("AppFace::addApps():: is called");
       for (AppRequest appRequest : appRequests) {
           appRequest.setStatus("ALLOWED");
           appService.create(appRequest);
       }
       log.info("AppFace::addApps():: is finished");
    }
}
