package com.gingerx.focusservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppRequest {
    private String appName;
    private String appId;
    private String status;  // RESTRICTED, ALLOWED
    private Long userId;

}
