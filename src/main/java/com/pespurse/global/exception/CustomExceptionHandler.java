package com.pespurse.global.exception;

import com.google.gson.JsonObject;
import com.pespurse.global.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    private static final String CODE = "code";
    private static final String MESSAGE = "message";
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> customExceptionHandler(CustomException customException) {
        log.warn("Custom Exception {}", customException.getMessage());
        return new ResponseEntity<>(Response.badRequest(customException.getResponseCode()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SystemFailureException.class)
    public ResponseEntity<Object> systemExceptionHandler(SystemFailureException systemFailureException) {
        log.warn("Custom Exception {}", systemFailureException.getMessage());
        return new ResponseEntity<>(Response.badRequest(systemFailureException.getResponseCode()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
