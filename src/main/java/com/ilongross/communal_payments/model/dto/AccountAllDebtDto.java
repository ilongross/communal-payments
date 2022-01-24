package com.ilongross.communal_payments.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AccountAllDebtDto {

    private Integer debtorsCounter;
    private BigDecimal totalDebt;

}
