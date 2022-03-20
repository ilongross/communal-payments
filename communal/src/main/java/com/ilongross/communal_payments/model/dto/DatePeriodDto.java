package com.ilongross.communal_payments.model.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class DatePeriodDto {

    private Integer accountId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
