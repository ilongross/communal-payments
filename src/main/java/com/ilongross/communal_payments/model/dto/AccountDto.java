package com.ilongross.communal_payments.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class AccountDto {

    private Integer id;
    private String name;
    private String lastname;
    private String patronymic;
    private BigDecimal square;
    private AddressDto address;
    private String email;

}
