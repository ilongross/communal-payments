package com.ilongross.communal_payments.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ServiceTypeDto {

    private Integer id;
    private String serviceName;
    private BigDecimal tariff;

}
