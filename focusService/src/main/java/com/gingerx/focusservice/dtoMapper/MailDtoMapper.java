package com.gingerx.focusservice.dtoMapper;

import com.gingerx.focusservice.dto.MailDto;

import java.util.Map;

public class MailDtoMapper {
    public static MailDto mapToMailDto(Object payload) {
        if (payload instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) payload;
            return MailDto.builder()
                    .recipient((String) map.get("recipient"))
                    .subject((String) map.get("subject"))
                    .msgBody((String) map.get("msgBody"))
                    .build();
        }
        throw new IllegalArgumentException("Invalid payload type");
    }
}
