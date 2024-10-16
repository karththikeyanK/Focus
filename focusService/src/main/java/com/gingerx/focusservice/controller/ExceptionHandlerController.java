package com.gingerx.focusservice.controller;

import com.gingerx.focus_v1.exception.GeneralException;
import com.gingerx.focus_v1.response.ApiErrorResponse;
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

}
