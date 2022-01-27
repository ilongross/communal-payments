package com.ilongross.communal_payments.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AccountReportDto {

    private Integer accountId;
    private DatePeriodDto period;
    private List<MeterReportDto> meterReportList;

}
