package com.ilongross.communal_payments.model.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class UserAuthDto {

    private Integer id;
    @NotNull
    private String login;
    @NotNull
    private String password;

}
