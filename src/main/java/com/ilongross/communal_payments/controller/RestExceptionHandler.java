package com.ilongross.communal_payments.controller;

import com.ilongross.communal_payments.exception.ServiceTypeException;
import com.ilongross.communal_payments.model.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ServiceTypeException.class)
    public ResponseEntity<ErrorDto> handleServiceTypeException(ServiceTypeException exception) {
        var errorDto = new ErrorDto("NOT FOUND", exception.getWrongServiceTypeId(), exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorDto);
    }

}
