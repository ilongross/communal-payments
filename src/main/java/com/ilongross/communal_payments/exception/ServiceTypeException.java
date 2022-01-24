package com.ilongross.communal_payments.exception;

public class ServiceTypeException extends RuntimeException {

    private Integer wrongServiceTypeId;

    public ServiceTypeException(String message, Integer serviceTypeId) {
        super(message);
        this.wrongServiceTypeId = serviceTypeId;
    }

    public Integer getWrongServiceTypeId() {
        return wrongServiceTypeId;
    }

    public void setWrongServiceTypeId(Integer wrongServiceTypeId) {
        this.wrongServiceTypeId = wrongServiceTypeId;
    }
}
