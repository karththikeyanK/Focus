package com.gingerx.emailservice.service;

import com.gingerx.emailservice.dto.MailDto;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
}