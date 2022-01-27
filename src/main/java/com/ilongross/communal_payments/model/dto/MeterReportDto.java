package com.ilongross.communal_payments.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Builder
@Getter
@Setter
public class MeterReportDto {

    private String serviceName;
    private BigDecimal addedSum;
    private BigDecimal paidSum;
    private BigDecimal currentDebt;

}
