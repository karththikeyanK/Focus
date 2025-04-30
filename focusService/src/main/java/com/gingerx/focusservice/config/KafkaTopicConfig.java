package com.gingerx.focusservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic sendEmailTopic() {
        return new NewTopic("send-email", 1, (short) 1);
    }

    @Bean
    public NewTopic sendPushNotificationTopic() {
        return new NewTopic("send-push-notification", 1, (short) 1);
    }

}
