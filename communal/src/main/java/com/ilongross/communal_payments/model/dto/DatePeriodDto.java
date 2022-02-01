package com.ilongross.communal_payments.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Builder
public class DatePeriodDto {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
