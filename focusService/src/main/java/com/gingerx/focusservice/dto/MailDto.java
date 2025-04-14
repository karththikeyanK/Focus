package com.gingerx.focusservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MailDto {
    private String recipient;
    private String subject;
    private String msgBody;
}
