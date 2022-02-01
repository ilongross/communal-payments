package com.ilongross.communal_payments.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDto {

    private Integer id;
    private String name;
    private String lastname;
    private String patronymic;
    private Integer address;
    private String email;

}
