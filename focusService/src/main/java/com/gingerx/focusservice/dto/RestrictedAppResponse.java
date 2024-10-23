package com.gingerx.focusservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestrictedAppResponse {
    private Long id;
    private String appName;
    private Long userId;
}
