package com.gingerx.focusservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApproverDto {
    private String email;
    private String vCode;
    private String deviceName;
    private List<AppAndDetailRequest> apps;
}
