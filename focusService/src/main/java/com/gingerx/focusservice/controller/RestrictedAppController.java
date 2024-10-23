package com.gingerx.focusservice.controller;

import com.gingerx.focusservice.dto.RestrictedAppRequest;
import com.gingerx.focusservice.dto.RestrictedAppResponse;
import com.gingerx.focusservice.response.ApiResponse;
import com.gingerx.focusservice.service.RestrictedAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restricted-app")
@RequiredArgsConstructor
public class RestrictedAppController {
    private final RestrictedAppService restrictedAppService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<RestrictedAppResponse>> create(RestrictedAppRequest restrictedAppRequest){
        return ResponseEntity.ok(new ApiResponse<>(ApiResponse.SUCCESS,"Restricted App Created",restrictedAppService.create(restrictedAppRequest)));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<RestrictedAppResponse>> update(@PathVariable Long id, RestrictedAppRequest restrictedAppRequest){
        return ResponseEntity.ok(new ApiResponse<>(ApiResponse.SUCCESS,"Restricted App Updated",restrictedAppService.update(id, restrictedAppRequest)));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<RestrictedAppResponse>> get(@PathVariable Long id){
        return ResponseEntity.ok(new ApiResponse<>(ApiResponse.SUCCESS,"Restricted App Retrieved",restrictedAppService.getRestrictedAppById(id)));
    }

    @GetMapping("/get-all-by-user/{userId}")
    public ResponseEntity<ApiResponse<List<RestrictedAppResponse>>> getAllByUser(@PathVariable Long userId){
        return ResponseEntity.ok(new ApiResponse<>(ApiResponse.SUCCESS,"Restricted App Retrieved",restrictedAppService.getAllRestrictedAppsByUserId(userId)));
    }

}