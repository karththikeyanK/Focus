package com.gingerx.focusservice.controller;

import com.gingerx.focusservice.exception.DataNotFoundException;
import com.gingerx.focusservice.exception.DuplicationException;
import com.gingerx.focusservice.exception.GeneralException;
import com.gingerx.focusservice.exception.JwtAuthenticationException;
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

}
