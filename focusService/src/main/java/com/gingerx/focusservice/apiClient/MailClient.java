package com.gingerx.focusservice.apiClient;

import com.gingerx.focusservice.dto.MailDto;
import com.gingerx.focusservice.exception.ApiClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class MailClient {

    @Value("${email-service.url}")
    private String EMAIL_URL_PATH;

    public void sendMail(MailDto mailDto) {
        log.info("MailClient::sendMail():: is called");
        RestTemplate restTemplate = new RestTemplate();

        try {
            HttpEntity<MailDto> request = new HttpEntity<>(mailDto);
            ResponseEntity<String> response = restTemplate.exchange(EMAIL_URL_PATH, HttpMethod.POST, request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("MailClient::sendMail()::Email sent successfully");
            } else {
                log.error("MailClient::sendMail()::Failed to send email");
            }
        } catch (RestClientException e) {
            log.error("EmailApiClient::sendEmail()::Exception occurred while sending email", e);
            throw new ApiClientException("Exception occurred while sending email");
        }

    }
}
