package com.ilongross.communal_payments.controller;

import com.ilongross.communal_payments.exception.*;
import com.ilongross.communal_payments.model.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ServiceTypeException.class)
    public ResponseEntity<ErrorDto> handleServiceTypeException(ServiceTypeException exception) {
        var error = new ErrorDto("NOT FOUND", exception.getWrongServiceTypeId(), exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(AccountIdNotFoundException.class)
    public ResponseEntity<ErrorDto> handleAccountNotFoundException(AccountIdNotFoundException exception) {
        var error = new ErrorDto("NOT FOUND", exception.getAccountIdNotFound(), exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<ErrorDto> handleIdNotFoundException(IdNotFoundException exception) {
        var error = new ErrorDto("NOT FOUND", exception.getIdNotFound(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException(UserNotFoundException exception) {
        var error = new ErrorDto("NOT FOUND", null, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<ErrorDto> handleUserExistsException(UserExistsException exception) {
        var error = new ErrorDto("EXISTS", null, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

}
