package com.ilongross.communal_payments.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AccountMeterDebtDto {

    private Integer id;
    private BigDecimal houseMaintenance;
    private BigDecimal currentMaintenance;
    private BigDecimal elevatorMaintenance;
    private BigDecimal garbageRemove;
    private BigDecimal electricity;
    private BigDecimal coldWater;
    private BigDecimal hotWater;

}
