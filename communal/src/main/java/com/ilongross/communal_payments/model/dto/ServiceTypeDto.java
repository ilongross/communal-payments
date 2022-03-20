package com.ilongross.communal_payments.model.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
public class ServiceTypeDto {

    @NotNull
    private Integer id;
    private String serviceName;
    @NotNull
    private BigDecimal tariff;

}
