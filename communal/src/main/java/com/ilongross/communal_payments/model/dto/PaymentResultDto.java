package com.ilongross.communal_payments.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentResultDto {

    private String address;
    private String accountInitials;
    private String serviceName;
    private BigDecimal sum;
    private LocalDateTime date;

}
