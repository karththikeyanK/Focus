package com.gingerx.emailservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gingerx.emailservice.dto.KafkaMessage;
import com.gingerx.emailservice.dto.MailDto;
import com.gingerx.emailservice.dtoMapper.MailDtoMapper;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendMail(MailDto email) {
        log.info("EmailService::sendMail():: is called");
        try {
            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true);

            helper.setFrom(sender);

            if (email.getRecipient().contains(",")) {
                String[] recipients = email.getRecipient().split(",");
                List<String> validRecipients = new ArrayList<>();
                for (String recipient : recipients) {
                    String trimmedRecipient = recipient.trim();
                    if (!trimmedRecipient.isEmpty()) {
                        validRecipients.add(trimmedRecipient);
                    }
                }
                if (!validRecipients.isEmpty()) {
                    helper.setTo(validRecipients.toArray(new String[0]));
                } else {
                    log.warn("EmailService::sendMail()::No valid recipients provided.");
                    return;
                }
            } else {
                String recipient = email.getRecipient().trim();
                if (!recipient.isEmpty()) {
                    helper.setTo(recipient);
                } else {
                    log.warn("EmailService::sendMail()::No valid recipient provided.");
                    return;
                }
            }

            helper.setSubject(email.getSubject());
            helper.setText(email.getMsgBody(), true);

            javaMailSender.send(mailMessage);
            log.info("Email sent successfully..");

        } catch (Exception e) {
            log.warn("Error while sending email: {}", e.getMessage());
        }
        log.info("EmailService::sendSimpleMail():: is finished");
    }

    @KafkaListener(topics = "send-email", groupId = "group_id")
    public void sendKafkaMail(String messageJson) {
        KafkaMessage kafkaMessage = null;
        try {
            kafkaMessage = new ObjectMapper().readValue(messageJson, KafkaMessage.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        log.info("EmailService::sendKafkaMail():: is called");
        MailDto email = MailDtoMapper.mapToMailDto(kafkaMessage.getPayload());
        try {
            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true);

            helper.setFrom(sender);

            if (email.getRecipient().contains(",")) {
                String[] recipients = email.getRecipient().split(",");
                List<String> validRecipients = new ArrayList<>();
                for (String recipient : recipients) {
                    String trimmedRecipient = recipient.trim();
                    if (!trimmedRecipient.isEmpty()) {
                        validRecipients.add(trimmedRecipient);
                    }
                }
                if (!validRecipients.isEmpty()) {
                    helper.setTo(validRecipients.toArray(new String[0]));
                } else {
                    log.warn("EmailService::sendKafkaMail()::No valid recipients provided.");
                    return;
                }
            } else {
                String recipient = email.getRecipient().trim();
                if (!recipient.isEmpty()) {
                    helper.setTo(recipient);
                } else {
                    log.warn("EmailService::sendKafkaMail()::No valid recipient provided.");
                    return;
                }
            }

            helper.setSubject(email.getSubject());
            helper.setText(email.getMsgBody(), true);

            javaMailSender.send(mailMessage);
            log.info("MailService::sendKafkaMail()::Email sent successfully..");

        } catch (Exception e) {
            log.warn("MailService::sendKafkaMail()::Error while sending email: {}", e.getMessage());
        }
        log.info("MailService::sendKafkaMail()::EmailService::sendSimpleMail():: is finished");
    }
}