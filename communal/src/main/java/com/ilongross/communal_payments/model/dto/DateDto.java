package com.ilongross.communal_payments.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DateDto {

    private Integer accountId;
    private String startDate;
    private String endDate;

}
