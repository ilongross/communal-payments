package com.ilongross.communal_payments.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
public class AccountIdNotFoundException extends RuntimeException {

    private final Integer accountIdNotFound;

    public AccountIdNotFoundException(Integer accountIdNotFound) {
        super("Account id not found");
        this.accountIdNotFound = accountIdNotFound;
    }
}
