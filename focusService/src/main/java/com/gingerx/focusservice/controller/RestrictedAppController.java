package com.gingerx.focusservice.controller;

import com.gingerx.focusservice.dto.AppAndDetailResponse;
import com.gingerx.focusservice.dto.AppRequest;
import com.gingerx.focusservice.dto.AppResponse;
import com.gingerx.focusservice.facade.AppFacade;
import com.gingerx.focusservice.response.ApiResponse;
import com.gingerx.focusservice.service.AppService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restricted-app")
@RequiredArgsConstructor
public class RestrictedAppController {
    private final AppService appService;
    private final AppFacade appFacade;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<AppResponse>> create(AppRequest appRequest){
        return ResponseEntity.ok(new ApiResponse<>(ApiResponse.SUCCESS,"Restricted App Created", appService.create(appRequest)));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<AppResponse>> update(@PathVariable Long id, AppRequest appRequest){
        return ResponseEntity.ok(new ApiResponse<>(ApiResponse.SUCCESS,"Restricted App Updated", appService.update(id, appRequest)));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<AppResponse>> get(@PathVariable Long id){
        return ResponseEntity.ok(new ApiResponse<>(ApiResponse.SUCCESS,"Restricted App Retrieved", appService.getRestrictedAppById(id)));
    }

    @GetMapping("/get-all-by-user/{userId}")
    public ResponseEntity<ApiResponse<List<AppResponse>>> getAllByUser(@PathVariable Long userId){
        return ResponseEntity.ok(new ApiResponse<>(ApiResponse.SUCCESS,"Restricted App Retrieved", appService.getAllRestrictedAppsByUserId(userId)));
    }

    @GetMapping("get-apps-user-and-approver/{userId}/{approverId}")
    public ResponseEntity<ApiResponse<List<AppAndDetailResponse>>> getAppsByUserAndApprover(@PathVariable Long userId, @PathVariable Long approverId){
        return ResponseEntity.ok(new ApiResponse<>(ApiResponse.SUCCESS,"Restricted App Retrieved", appFacade.getAppsByUserIdAndApproverId(userId,approverId)));
    }

}