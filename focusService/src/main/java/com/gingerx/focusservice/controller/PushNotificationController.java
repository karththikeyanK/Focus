package com.gingerx.focusservice.controller;


import com.gingerx.focusservice.dto.NotificationDto;
import com.gingerx.focusservice.response.ApiResponse;
import com.gingerx.focusservice.service.FCMService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/push-notification")
public class PushNotificationController {

    private final FCMService fcmService;

    @PostMapping("/send")
    public ResponseEntity<ApiResponse<Void>> sendPushNotification(@RequestBody NotificationDto notificationDto) {
        fcmService.sendPushNotification(notificationDto);
        return ResponseEntity.ok(new ApiResponse<>(ApiResponse.SUCCESS, "Push notification sent successfully", null));
    }

}
