package com.gingerx.focusservice.facade;

import com.gingerx.focusservice.dto.KafkaMessage;
import com.gingerx.focusservice.dto.MailDto;
import com.gingerx.focusservice.dto.UserRequest;
import com.gingerx.focusservice.entity.User;
import com.gingerx.focusservice.service.UserService;
import com.gingerx.focusservice.strategy.mailTemplate.OtpMailTemplateStrategy;
import com.gingerx.focusservice.util.DurationUtil;
import com.gingerx.focusservice.util.OtpGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationFacade {
    private final UserService userService;
    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;
    private final OtpMailTemplateStrategy otpMailTemplateStrategy;

    @Transactional
    public User registerUser(UserRequest userRequest) {
        log.info("RegistrationFacade::Registering user started");
        String otp = OtpGenerator.generateOtp();
        userRequest.setVCode(otp);
        userRequest.setVCodeTime(DurationUtil.getFiveMinutesLater(LocalDateTime.now()));
        User user = userService.Register(userRequest);
        log.info("RegistrationFacade::User registered successfully");

        String body = otpMailTemplateStrategy.getOtpMailTemplate(otp);
        MailDto mailDto = MailDto.builder()
                .recipient(user.getEmail())
                .subject("OTP Verification")
                .msgBody(body)
                .build();

        KafkaMessage message = KafkaMessage.builder()
                .type("MailDto")
                .payload(mailDto)
                .build();

        kafkaTemplate.send("send-email", message);

        log.info("RegistrationFacade::Registering user completed");
        return user;
    }
}