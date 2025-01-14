package com.gingerx.focusservice.util;

import org.springframework.stereotype.Component;

@Component
public class OtpGenerator {
    public static String generateOtp() {
        int randomPin = (int) (Math.random() * 9000) + 1000;
        return String.valueOf(randomPin);
    }
}
