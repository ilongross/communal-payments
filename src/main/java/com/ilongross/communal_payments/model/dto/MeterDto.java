package com.ilongross.communal_payments.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class MeterDto {

    private Integer id;
    private Integer accountID;
    private Integer service;
    private BigDecimal value;
    private LocalDate date;

}
