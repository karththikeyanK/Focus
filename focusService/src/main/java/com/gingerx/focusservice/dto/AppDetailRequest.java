package com.gingerx.focusservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppDetailRequest {
    private String appName;
    private String appId;
    private String appImageUrl;
    private String appImage;
}
