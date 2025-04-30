package com.gingerx.focusservice.exception;

public class VerificationException extends RuntimeException {
    public VerificationException(String message) {
        super(message);
    }

    public VerificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public VerificationException(Throwable cause) {
        super(cause);
    }
}
