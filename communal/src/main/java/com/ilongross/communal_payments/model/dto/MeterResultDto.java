package com.ilongross.communal_payments.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class MeterResultDto {

    private String accountInitials;
    private String service;
    private BigDecimal value;
    private LocalDateTime date;

}
