package com.ilongross.communal_payments.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAuthDto {

    private String login;
    private String password;

}
