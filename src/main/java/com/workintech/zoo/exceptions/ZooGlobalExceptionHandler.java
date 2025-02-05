package com.workintech.zoo.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ZooGlobalExceptionHandler {

    // Özel olarak fırlatılan hataları yakalar
    @ExceptionHandler(ZooException.class)
    public ResponseEntity<ZooErrorResponse> handleException(ZooException zooException) {
        ZooErrorResponse zooErrorResponse = new ZooErrorResponse(
                zooException.getHttpStatus().value(),
                zooException.getLocalizedMessage(),
                System.currentTimeMillis()
        );
        log.error("ZooException occurred = ", zooException);
        return new ResponseEntity<>(zooErrorResponse, zooException.getHttpStatus());
    }

    // Genel bilinmeyen hataları yakalar
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ZooErrorResponse> handleGenericException(Exception exception) {
        ZooErrorResponse zooErrorResponse = new ZooErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getLocalizedMessage(),
                System.currentTimeMillis()
        );
        log.error("ZooException occurred = ", exception);
        return new ResponseEntity<>(zooErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
