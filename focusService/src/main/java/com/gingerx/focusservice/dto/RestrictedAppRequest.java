package com.gingerx.focusservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestrictedAppRequest {
    private String appName;
    private Long userId;
}
