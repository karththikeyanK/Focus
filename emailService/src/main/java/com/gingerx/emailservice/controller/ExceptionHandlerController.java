package com.gingerx.emailservice.controller;

import com.gingerx.emailservice.exception.MailException;
import com.gingerx.emailservice.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        return ResponseEntity.badRequest().body(new ApiResponse<>(ApiResponse.ERROR,ex.getMessage(),null));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleException(RuntimeException ex) {
        return ResponseEntity.badRequest().body(new ApiResponse<>(ApiResponse.ERROR,ex.getMessage(),null));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new ApiResponse<>(ApiResponse.ERROR,ex.getMessage(),null));
    }


    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiResponse<Void>> handleException(NullPointerException ex) {
        return ResponseEntity.badRequest().body(new ApiResponse<>(ApiResponse.ERROR,ex.getMessage(),null));
    }

    @ExceptionHandler(MailException.class)
    public ResponseEntity<ApiResponse<Void>> handleException(MailException ex) {
        return ResponseEntity.badRequest().body(new ApiResponse<>(ApiResponse.ERROR,ex.getMessage(),null));
    }
}