package com.ilongross.communal_payments.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
public class IdNotFoundException extends RuntimeException {

    private final Integer idNotFound;

    public IdNotFoundException(Integer id) {
        super("Not found");
        this.idNotFound = id;
    }
}
