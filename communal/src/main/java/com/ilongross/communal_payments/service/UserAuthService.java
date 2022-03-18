package com.ilongross.communal_payments.service;

import com.ilongross.communal_payments.model.dto.UserAuthDto;
import com.ilongross.communal_payments.model.dto.UserInfoDto;
import com.ilongross.communal_payments.model.entity.UserAuthEntity;

import java.util.List;

public interface UserAuthService {

    UserInfoDto create(UserAuthDto userAuthDto);
    UserInfoDto authenticate(UserAuthDto userAuthDto);
    List<UserInfoDto> getAll();

}
