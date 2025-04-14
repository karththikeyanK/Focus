package com.gingerx.focusservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppResponse {
    private Long id;
    private String appName;
    private String appId;
    private String status;  // RESTRICTED, ALLOWED
    private Long userId;
}
