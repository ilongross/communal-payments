package com.ilongross.communal_payments.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class MeterDto {

    private Integer id;
    private Integer userId;
    private String service;
    private BigDecimal value;

}
