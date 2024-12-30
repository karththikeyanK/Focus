package com.eimsky.mailer.v01.controller;

import com.eimsky.mailer.v01.exception.ApiClientException;
import com.eimsky.mailer.v01.response.ApiExceptionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExceptionResponse> handleException(Exception ex) {
        log.error("Exception: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new ApiExceptionResponse(ApiExceptionResponse.ERROR,ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiExceptionResponse> handleRuntimeException(RuntimeException ex) {
        log.error("RuntimeException: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new ApiExceptionResponse(ApiExceptionResponse.ERROR,ex.getMessage()));
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ApiExceptionResponse> handleJsonProcessingException(JsonProcessingException ex) {
        log.error("JsonProcessingException: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new ApiExceptionResponse(ApiExceptionResponse.ERROR,ex.getMessage()));
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity<ApiExceptionResponse> handleIndexOutOfBoundsException(IndexOutOfBoundsException ex) {
        log.error("IndexOutOfBoundsException: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new ApiExceptionResponse(ApiExceptionResponse.ERROR,ex.getMessage()));
    }


    @ExceptionHandler(ClassNotFoundException.class)
    public ResponseEntity<ApiExceptionResponse> handleClassNotFoundException(ClassNotFoundException ex) {
        log.error("ClassNotFoundException: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new ApiExceptionResponse(ApiExceptionResponse.ERROR,ex.getMessage()));
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiExceptionResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("IllegalArgumentException: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new ApiExceptionResponse(ApiExceptionResponse.ERROR,ex.getMessage()));
    }

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<ApiExceptionResponse> handleRestClientException(RestClientException ex) {
        log.error("RestClientException: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new ApiExceptionResponse(ApiExceptionResponse.ERROR,ex.getMessage()));
    }

    @ExceptionHandler(ApiClientException.class)
    public ResponseEntity<ApiExceptionResponse> handleApiClientException(ApiClientException ex) {
        log.error("ApiClientException: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new ApiExceptionResponse(ApiExceptionResponse.ERROR,ex.getMessage()));
    }


}