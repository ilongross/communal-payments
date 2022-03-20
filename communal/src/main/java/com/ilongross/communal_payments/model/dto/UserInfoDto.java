package com.ilongross.communal_payments.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserInfoDto {

    private Integer id;
    private String login;
    private List<String> roles;

}
