package com.ilongross.communal_payments.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class MeterDto {

    private Integer id;
    private Integer accountId;
    private Integer serviceId;
    private BigDecimal value;
    private LocalDateTime date;

}
