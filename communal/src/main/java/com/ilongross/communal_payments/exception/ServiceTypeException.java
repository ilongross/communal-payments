package com.ilongross.communal_payments.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceTypeException extends RuntimeException {

    private Integer wrongServiceTypeId;

    public ServiceTypeException(Integer serviceTypeId) {
        super("Wrong service type");
        this.wrongServiceTypeId = serviceTypeId;
    }
}
