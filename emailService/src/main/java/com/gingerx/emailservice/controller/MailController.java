package com.gingerx.emailservice.controller;

import com.gingerx.emailservice.dto.MailDto;
import com.gingerx.emailservice.response.ApiResponse;
import com.gingerx.emailservice.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mail")
public class MailController {

    private final MailService mailService;

    @PostMapping("/send")
    public ResponseEntity<ApiResponse<Void>> sendMail(@RequestBody MailDto mailDto) {
        mailService.sendMail(mailDto);
        return ResponseEntity.ok(new ApiResponse<>(ApiResponse.SUCCESS, "Email sent successfully", null));
    }
}
