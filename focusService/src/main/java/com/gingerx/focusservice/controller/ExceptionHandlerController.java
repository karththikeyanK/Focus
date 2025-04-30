package com.gingerx.focusservice.controller;

import com.gingerx.focusservice.exception.*;
import com.gingerx.focusservice.response.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiErrorResponse> handleGeneralException(GeneralException e){
        log.error("ExceptionHandlerController: GeneralException: {}", e.getMessage());
        return ResponseEntity.badRequest().body(new ApiErrorResponse(ApiErrorResponse.ERROR, e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception e){
        log.error("ExceptionHandlerController: Exception: {}", e.getMessage());
        return ResponseEntity.badRequest().body(new ApiErrorResponse(ApiErrorResponse.ERROR, e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorResponse> handleRuntimeException(RuntimeException e){
        log.error("ExceptionHandlerController: RuntimeException: {}", e.getMessage());
        return ResponseEntity.badRequest().body(new ApiErrorResponse(ApiErrorResponse.ERROR, e.getMessage()));
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handleJwtAuthenticationException(JwtAuthenticationException e){
        log.error("ExceptionHandlerController: JwtAuthenticationException: {}", e.getMessage());
        return ResponseEntity.badRequest().body(new ApiErrorResponse(ApiErrorResponse.ERROR, e.getMessage()));
    }

    @ExceptionHandler(DuplicationException.class)
    public ResponseEntity<ApiErrorResponse> handleDuplicationException(DuplicationException e){
        log.error("ExceptionHandlerController: DuplicationException: {}", e.getMessage());
        return ResponseEntity.badRequest().body(new ApiErrorResponse(ApiErrorResponse.ERROR, e.getMessage()));
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleDataNotFoundException(DataNotFoundException e){
        log.error("ExceptionHandlerController: DataNotFoundException: {}", e.getMessage());
        return ResponseEntity.badRequest().body(new ApiErrorResponse(ApiErrorResponse.ERROR, e.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e){
        log.error("ExceptionHandlerController: ResourceNotFoundException: {}", e.getMessage());
        return ResponseEntity.badRequest().body(new ApiErrorResponse(ApiErrorResponse.ERROR, e.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(IllegalArgumentException e){
        log.error("ExceptionHandlerController: IllegalArgumentException: {}", e.getMessage());
        return ResponseEntity.badRequest().body(new ApiErrorResponse(ApiErrorResponse.ERROR, e.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(AuthenticationException e){
        log.error("ExceptionHandlerController: AuthenticationException: {}", e.getMessage());
        return ResponseEntity.badRequest().body(new ApiErrorResponse(ApiErrorResponse.ERROR, e.getMessage()));
    }

    @ExceptionHandler(VerificationException.class)
    public ResponseEntity<ApiErrorResponse> handleExpiredDataException(VerificationException e){
        log.error("ExceptionHandlerController: VerificationException: {}", e.getMessage());
        return ResponseEntity.badRequest().body(new ApiErrorResponse(ApiErrorResponse.ERROR, e.getMessage()));
    }

}
