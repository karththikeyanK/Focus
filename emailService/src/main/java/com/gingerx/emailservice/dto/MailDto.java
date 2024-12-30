package com.gingerx.emailservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MailDto {
    private String recipient;
    private String subject;
    private String msgBody;
}
