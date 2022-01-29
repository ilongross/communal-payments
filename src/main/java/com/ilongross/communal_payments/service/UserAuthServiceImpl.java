package com.ilongross.communal_payments.service;

import com.ilongross.communal_payments.model.dto.UserAuthDto;
import com.ilongross.communal_payments.model.entity.UserAuthEntity;
import com.ilongross.communal_payments.repository.UserAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    private final UserAuthRepository userAuthRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public String create(UserAuthDto userAuthDto) {

        //TODO реализовать обработку исключения при неуспешной аутентификации и авторизации

        var userAuthEntity = new UserAuthEntity();
        userAuthEntity.setUsername(userAuthDto.getLogin());
        userAuthEntity.setPassword(passwordEncoder.encode(userAuthDto.getPassword()));
        return userAuthRepository.save(userAuthEntity).getUsername();
    }
}
