package com.ilongross.communal_payments.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AccountDebtDto {

    private Integer accountId;
    private BigDecimal debt;

}