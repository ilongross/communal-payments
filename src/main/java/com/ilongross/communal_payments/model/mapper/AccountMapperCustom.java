package com.ilongross.communal_payments.model.mapper;

import com.ilongross.communal_payments.model.dto.AccountDebtDto;
import com.ilongross.communal_payments.model.dto.AccountDto;
import com.ilongross.communal_payments.model.dto.MeterDto;
import com.ilongross.communal_payments.model.entity.AccountDebtEntity;
import com.ilongross.communal_payments.model.entity.AccountEntity;
import com.ilongross.communal_payments.model.entity.MeterEntity;
import com.ilongross.communal_payments.repository.AccountRepository;
import com.ilongross.communal_payments.repository.AddressRepository;
import com.ilongross.communal_payments.repository.ServiceTypeRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountMapperCustom {

    private final AccountRepository accountRepository;
    private final AddressRepository addressRepository;
    private final ServiceTypeRepository serviceTypeRepository;

    public AccountMapperCustom(AccountRepository accountRepository, AddressRepository addressRepository, ServiceTypeRepository serviceTypeRepository) {
        this.accountRepository = accountRepository;
        this.addressRepository = addressRepository;
        this.serviceTypeRepository = serviceTypeRepository;
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
        var addressEntity = addressRepository
                .findById(dto.getAddress()).orElseThrow(()-> new RuntimeException("Address not found."));
        var accountEntity = new AccountEntity();
        accountEntity.setName(dto.getName());
        accountEntity.setLastname(dto.getLastname());
        accountEntity.setPatronymic(dto.getPatronymic());
        accountEntity.setAddress(addressEntity);
        accountEntity.setEmail(dto.getEmail());
        return accountRepository.save(accountEntity);
    }

    public AccountDebtDto mapToDto (AccountDebtEntity entity) {
        return AccountDebtDto.builder()
                .accountId(entity.getAccountId())
                .debt(new BigDecimal(entity.getDebt().toString()))
                .build();
    }

    public MeterEntity mapToEntity(MeterDto dto) {
        var accountEntity = accountRepository
                .findById(dto.getAccountId())
                .orElseThrow(()-> new RuntimeException("Account not found."));
        var serviceTypeEntity = serviceTypeRepository
                .findById(dto.getServiceId()).
                orElseThrow(()-> new RuntimeException("Service type not found."));
        var entity = new MeterEntity();
        entity.setAccountId(accountEntity);
        entity.setServiceId(serviceTypeEntity);
        entity.setValue(dto.getValue());
        entity.setDate(dto.getDate());
        return entity;
    }

    public MeterDto mapToDto(MeterEntity entity) {
        return MeterDto.builder()
                .id(entity.getId())
                .accountId(entity.getAccountId().getId())
                .serviceId(entity.getServiceId().getId())
                .value(entity.getValue())
                .date(entity.getDate())
                .build();
    }

}
