package com.gingerx.focusservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppAndDetailResponse {
    private AppResponse appResponse;
    private AppDetailResponse appDetailResponse;
}
