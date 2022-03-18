package com.ilongross.communal_payments.model.mapper;

import com.ilongross.communal_payments.model.dto.UserAuthDto;
import com.ilongross.communal_payments.model.dto.UserInfoDto;
import com.ilongross.communal_payments.model.entity.RoleEntity;
import com.ilongross.communal_payments.model.entity.UserAuthEntity;
import com.ilongross.communal_payments.repository.RoleAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAuthMapper {

    private final RoleAuthRepository roleAuthRepository;

    public UserInfoDto mapToDto(UserAuthEntity entity) {
        var roles = entity.getRoles().stream().map(RoleEntity::getName).toList();
        return UserInfoDto.builder()
                .id(entity.getId())
                .login(entity.getUsername())
                .roles(roles)
                .build();
    }

}
