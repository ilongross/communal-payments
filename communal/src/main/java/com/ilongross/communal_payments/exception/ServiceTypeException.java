package com.ilongross.communal_payments.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ServiceTypeException extends RuntimeException {

    private final Integer wrongServiceTypeId;

    public ServiceTypeException(Integer serviceTypeId) {
        super("Wrong service type");
        this.wrongServiceTypeId = serviceTypeId;
    }
}
