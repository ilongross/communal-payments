package com.ilongross.communal_payments.model.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class AccountDto {

    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private String lastname;
    @NotNull
    private String patronymic;
    @NotNull
    private Integer address;
    @NotNull
    private String email;

}
