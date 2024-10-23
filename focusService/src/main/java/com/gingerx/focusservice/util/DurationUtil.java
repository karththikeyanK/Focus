package com.gingerx.focusservice.util;

import com.gingerx.focusservice.dto.DurationDto;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class DurationUtil {

    public static Duration convertToDuration(DurationDto durationDto) {
        Duration duration = Duration.ZERO;

        if (durationDto.getDays() != null) {
            duration = duration.plusDays(durationDto.getDays());
        }

        if (durationDto.getHours() != null) {
            duration = duration.plusHours(durationDto.getHours());
        }

        if (durationDto.getMinutes() != null) {
            duration = duration.plusMinutes(durationDto.getMinutes());
        }

        return duration;
    }

    public static DurationDto formatDuration(Duration duration) {
        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;

        return new DurationDto((int) days, (int) hours, (int) minutes);
    }


}