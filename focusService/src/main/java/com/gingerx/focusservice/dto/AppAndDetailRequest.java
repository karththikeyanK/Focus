package com.gingerx.focusservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppAndDetailRequest {
    private AppRequest appRequest;
    private AppDetailRequest appDetailRequest;
}
