package com.gingerx.focusservice.enums;

import lombok.Getter;

@Getter
public enum VerifiedEnum {
    VERIFIED("VERIFIED"),
    UNVERIFIED("UNVERIFIED");

    private final String value;

    VerifiedEnum(String value) {
        this.value = value;
    }

}
