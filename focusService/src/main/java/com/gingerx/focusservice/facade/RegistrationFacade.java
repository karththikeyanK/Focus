package com.gingerx.focusservice.facade;

import com.gingerx.focusservice.dto.*;
import com.gingerx.focusservice.dtoMapper.UserDtoMapper;
import com.gingerx.focusservice.entity.User;
import com.gingerx.focusservice.enums.ActiveStatus;
import com.gingerx.focusservice.exception.AuthenticationException;
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

    public AuthenticationResponse verifyOtp(AuthenticationRequest request) {
        log.info("RegistrationFacade::verifyOtp()::Verifying OTP started");
        User user = userService.findByEmail(request.getEmail());
        if (user == null) {
            log.error("RegistrationFacade::verifyOtp()::User not found");
            throw new AuthenticationException("User not found");
        }
        if (user.getVCode().isEmpty() || user.getVCodeTime() == null) {
            log.error("RegistrationFacade::verifyOtp()::OTP not found");
            throw new AuthenticationException("OTP not found.Please request for OTP again");
        }
        if (!user.getVCode().equals(request.getOtp())) {
            log.error("RegistrationFacade::verifyOtp()::Invalid OTP");
            throw new AuthenticationException("Invalid OTP");
        }
        if (user.getVCodeTime().isBefore(LocalDateTime.now())) {
            log.error("RegistrationFacade::verifyOtp()::OTP expired");
            throw new AuthenticationException("OTP expired");
        }
        user.setStatus(ActiveStatus.ACTIVE.name());
        user.setVCode(null);
        user.setVCodeTime(null);
        userService.update(user.getId(), UserDtoMapper.mapToRequest(user));
        log.info("RegistrationFacade::verifyOtp()::OTP verified successfully");
        return AuthenticationResponse.builder()
                .token(null)
                .id(user.getId())
                .build();
    }

    public AuthenticationResponse resendOtp(AuthenticationRequest authenticationRequest) {
        log.info("RegistrationFacade::resendOtp()::Resending OTP started");
        User user = userService.findByEmail(authenticationRequest.getEmail());
        if (user == null) {
            log.error("RegistrationFacade::resendOtp()::User not found");
            throw new AuthenticationException("User not found");
        }
        if (user.getStatus().equals(ActiveStatus.ACTIVE.name())) {
            log.error("RegistrationFacade::resendOtp()::User already active");
            throw new AuthenticationException("User already active");
        }
        if (user.getVCodeTime().isAfter(LocalDateTime.now())) {
            log.error("RegistrationFacade::resendOtp()::OTP not expired");
            throw new AuthenticationException("OTP not expired");
        }
        String otp = OtpGenerator.generateOtp();
        user.setVCode(otp);
        user.setVCodeTime(DurationUtil.getFiveMinutesLater(LocalDateTime.now()));
        userService.update(user.getId(), UserDtoMapper.mapToRequest(user));

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

        log.info("RegistrationFacade::resendOtp()::Resending OTP completed");
        return AuthenticationResponse.builder()
                .token(null)
                .id(user.getId())
                .build();
    }
}