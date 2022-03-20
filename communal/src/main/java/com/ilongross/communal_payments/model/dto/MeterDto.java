package com.ilongross.communal_payments.model.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class MeterDto {

    private Integer id;
    @NotNull
    private Integer accountId;
    @NotNull
    private Integer serviceId;
    @NotNull
    private BigDecimal value;
    private LocalDateTime date;

}
