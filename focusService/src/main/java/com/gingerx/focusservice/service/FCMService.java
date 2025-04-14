package com.gingerx.focusservice.service;

import com.gingerx.focusservice.dto.NotificationDto;
import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class FCMService {

    public void sendPushNotification(NotificationDto notificationDto) {
        log.info("Sending FCM notification with title: {}", notificationDto.getTitle());

        Notification notification = Notification.builder()
                .setTitle(notificationDto.getTitle())
                .setBody(notificationDto.getBody())
                .build();

        Message message = Message.builder()
                .setToken(notificationDto.getRecipientToken()) // recipient token
                .setNotification(notification)
                .putAllData(notificationDto.getData()) // additional key-values
                .build();

        log.info("Sending FCM notification send successfully with title: {}", notificationDto.getTitle());
        try {
            FirebaseMessaging.getInstance().sendAsync(message).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}

