package com.ilongross.communal_payments.model.mapper;

import com.ilongross.communal_payments.model.dto.AccountDto;
import com.ilongross.communal_payments.model.entity.AccountEntity;
import com.ilongross.communal_payments.repository.AccountRepository;
import com.ilongross.communal_payments.repository.AddressRepository;
import org.springframework.stereotype.Component;

@Component
public class AccountMapperCustom {

    private final AccountRepository accountRepository;

    public AccountMapperCustom(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDto mapToDto(AccountEntity entity) {
        return AccountDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .lastname(entity.getLastname())
                .patronymic(entity.getPatronymic())
                .address(entity.getAddress().getId())
                .email(entity.getEmail())
                .build();
    }

    public AccountEntity mapToEntity(AccountDto dto) {
        return accountRepository.findById(dto.getId()).get();
    }


}
