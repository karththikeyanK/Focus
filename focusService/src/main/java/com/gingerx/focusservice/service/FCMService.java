package com.gingerx.focusservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gingerx.focusservice.dto.NotificationDto;
import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
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

    @KafkaListener(topics = "send-push-notification", groupId = "group_id")
    public void sendPushNotificationThroughKafka(String messageJson) {
        log.info("FCMService::sendPushNotificationThroughKafka()::Sending FCM notification started");

        NotificationDto notificationDto = null;
        try {
            notificationDto = new ObjectMapper().readValue(messageJson, NotificationDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Notification notification = Notification.builder()
                .setTitle(notificationDto.getTitle())
                .setBody(notificationDto.getBody())
                .build();

        Message message = Message.builder()
                .setToken(notificationDto.getRecipientToken()) // recipient token
                .setNotification(notification)
                .putAllData(notificationDto.getData()) // additional key-values
                .build();

        log.info("FCMService::sendPushNotificationThroughKafka()::Sending FCM notification send successfully with title: {}", notificationDto.getTitle());
        try {
            FirebaseMessaging.getInstance().sendAsync(message).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}

