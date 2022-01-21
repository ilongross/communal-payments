package com.ilongross.communal_payments.model.dto;

import com.ilongross.communal_payments.model.entity.AddressEntity;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;

@Data
@Builder
public class AccountCreateDto {

    private Integer id;
    private String name;
    private String lastname;
    private String patronymic;
    private String region;
    private String city;
    private String street;
    private String house;
    private String apartment;
    private BigDecimal square;
    private String email;

}
