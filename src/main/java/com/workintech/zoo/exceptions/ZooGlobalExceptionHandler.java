package com.workintech.zoo.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ZooGlobalExceptionHandler {
    private ZooException zooException;
    private ZooErrorResponse zooErrorResponse;

    @ExceptionHandler(ZooException.class)
    public ResponseEntity<ZooErrorResponse> handleException(ZooException zooException){
        ZooErrorResponse errorResponse = new ZooErrorResponse(zooException.getStatus().value(), zooException.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, zooException.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ZooErrorResponse> handleException(Exception exception){
        ZooErrorResponse errorResponse = new ZooErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
