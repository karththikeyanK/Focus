package com.gingerx.focusservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotificationDto {
    private String title;
    private String body;
    private String recipientToken;
    private Map<String, String> data;
}
