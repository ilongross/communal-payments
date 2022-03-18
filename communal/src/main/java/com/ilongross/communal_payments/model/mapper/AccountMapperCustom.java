package com.ilongross.communal_payments.model.mapper;

import com.ilongross.communal_payments.exception.AccountIdNotFoundException;
import com.ilongross.communal_payments.exception.IdNotFoundException;
import com.ilongross.communal_payments.model.dto.*;
import com.ilongross.communal_payments.model.entity.AccountDebtEntity;
import com.ilongross.communal_payments.model.entity.AccountEntity;
import com.ilongross.communal_payments.model.entity.AccountMeterDebtEntity;
import com.ilongross.communal_payments.model.entity.MeterEntity;
import com.ilongross.communal_payments.repository.AccountRepository;
import com.ilongross.communal_payments.repository.AddressRepository;
import com.ilongross.communal_payments.repository.ServiceTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AccountMapperCustom {

    private final AccountRepository accountRepository;
    private final AddressRepository addressRepository;
    private final ServiceTypeRepository serviceTypeRepository;
    private final AddressMapper addressMapper;

    public AccountDto mapToDto(AccountEntity entity) {
        var address = addressRepository
                .findById(entity.getAddress().getId())
                .orElseThrow(()-> new IdNotFoundException(entity.getAddress().getId()));
        return AccountDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .lastname(entity.getLastname())
                .patronymic(entity.getPatronymic())
                .address(address.getId())
                .email(entity.getEmail())
                .build();
    }

    public AccountEntity mapToEntity(AccountDto dto) {
        var addressEntity = addressRepository
                .findById(dto.getAddress())
                .orElseThrow(()-> new IdNotFoundException(dto.getAddress()));
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

    public MeterResultDto mapToMeterResultDto(MeterEntity entity) {
        var accountEntity = entity.getAccountId();
        var serviceEntity = entity.getServiceId();
        var initialsString = String.format(
                "%s %s %s",
                accountEntity.getLastname(),
                accountEntity.getName(),
                accountEntity.getPatronymic());
        return MeterResultDto.builder()
                .accountInitials(initialsString)
                .service(serviceEntity.getServiceName())
                .value(entity.getValue())
                .date(entity.getDate())
                .build();
    }

    public AccountMeterDebtDto mapToDto(AccountMeterDebtEntity entity) {
        return AccountMeterDebtDto.builder()
                .id(entity.getId())
                .houseMaintenance(entity.getHouseMaintenance())
                .currentMaintenance(entity.getCurrentMaintenance())
                .elevatorMaintenance(entity.getElevatorMaintenance())
                .garbageRemove(entity.getGarbageRemove())
                .electricity(entity.getElectricity())
                .coldWater(entity.getColdWater())
                .hotWater(entity.getHotWater())
                .build();
    }

}
