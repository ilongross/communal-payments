package com.ilongross.communal_payments.service;

import com.ilongross.communal_payments.exception.IdNotFoundException;
import com.ilongross.communal_payments.exception.UserExistsException;
import com.ilongross.communal_payments.exception.UserNotFoundException;
import com.ilongross.communal_payments.model.dto.UserAuthDto;
import com.ilongross.communal_payments.model.dto.UserInfoDto;
import com.ilongross.communal_payments.model.entity.RoleEntity;
import com.ilongross.communal_payments.model.entity.UserAuthEntity;
import com.ilongross.communal_payments.model.mapper.UserAuthMapper;
import com.ilongross.communal_payments.repository.RoleAuthRepository;
import com.ilongross.communal_payments.repository.UserAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService{

    private final UserAuthRepository userAuthRepository;
    private final RoleAuthRepository roleAuthRepository;

    @Override
    public UserInfoDto create(UserAuthDto userAuthDto) {
        var isPresent = userAuthRepository
                .findByUsername(userAuthDto.getLogin()).isPresent();
        var userInfo = UserInfoDto.builder();
        if(!isPresent) {
            var entity = new UserAuthEntity();
            var role = roleAuthRepository
                    .findByName("user").orElseThrow(()-> new IdNotFoundException(null));
            entity.setUsername(userAuthDto.getLogin());
            entity.setPassword(userAuthDto.getPassword());
            entity.setRoles(List.of(role));
            var entityCreated = userAuthRepository.save(entity);
            userInfo
                    .id(entityCreated.getId())
                    .login(entityCreated.getUsername())
                    .roles(List.of(role.getName()));
        } else {
            throw new UserExistsException("User already exists.");
        }
        return userInfo.build();
    }

    @Override
    public UserInfoDto authenticate(UserAuthDto userAuthDto) {
        return null;
    }

    @Override
    public List<UserInfoDto> getAll() {
        var entities = userAuthRepository.findAll();
        var userInfoList = new ArrayList<UserInfoDto>();
        for (var e : entities) {
            var roles = new ArrayList<String>();
            for (var role : e.getRoles()) {
                roles.add(role.getName());
            }
            userInfoList.add(UserInfoDto.builder()
                    .id(e.getId()).login(e.getUsername()).roles(roles)
                    .build());
        }
        return userInfoList;
    }



}
