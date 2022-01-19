package com.ilongross.communal_payments.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PaymentDto {

    private Integer id;
    private Integer accountId;
    private Integer serviceId;
    private BigDecimal sum;

}
