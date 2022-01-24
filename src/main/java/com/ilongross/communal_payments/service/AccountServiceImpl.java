package com.ilongross.communal_payments.service;

import com.ilongross.communal_payments.exception.ServiceTypeException;
import com.ilongross.communal_payments.model.dto.*;
import com.ilongross.communal_payments.model.entity.AccountDebtEntity;
import com.ilongross.communal_payments.model.entity.AccountMeterDebtEntity;
import com.ilongross.communal_payments.model.mapper.AccountMapperCustom;
import com.ilongross.communal_payments.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapperCustom accountMapperCustom;
    private final AccountDebtRepository accountDebtRepository;
    private final MeterRepository meterRepository;
    private final ServiceTypeRepository serviceTypeRepository;
    private final AccountMeterDebtRepository accountMeterDebtRepository;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapperCustom accountMapperCustom, AccountDebtRepository accountDebtRepository, MeterRepository meterRepository, ServiceTypeRepository serviceTypeRepository, AccountMeterDebtRepository accountMeterDebtRepository) {
        this.accountRepository = accountRepository;
        this.accountMapperCustom = accountMapperCustom;
        this.accountDebtRepository = accountDebtRepository;
        this.meterRepository = meterRepository;
        this.serviceTypeRepository = serviceTypeRepository;
        this.accountMeterDebtRepository = accountMeterDebtRepository;
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(accountMapperCustom::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto getAccountById(Integer accountId) {
        return accountMapperCustom
                .mapToDto(accountRepository.findById(accountId)
                        .orElseThrow(() -> new RuntimeException("Account not found.")));
    }

    @Override
    public AccountDebtDto getAccountDebtByAccountId(Integer accountId) {
        return accountMapperCustom
                .mapToDto(
                        accountDebtRepository
                                .findById(accountId)
                                .orElseThrow(() -> new RuntimeException("Account in debt table not found")));
    }

    @Override
    @Transactional
    public AccountDto createNewAccount(AccountDto dto) {
        var entity = accountRepository
                .save(accountMapperCustom.mapToEntity(dto));
        var accountDebtEntity = new AccountDebtEntity();
//        accountDebtEntity.setAccountId(entity.getId());
        accountDebtEntity.setDebt(new BigDecimal("0.00"));
        accountDebtRepository.save(accountDebtEntity);
        var accountMeterDebtEntity = new AccountMeterDebtEntity();
        accountMeterDebtEntity.setHouseMaintenance(new BigDecimal("0.00"));
        accountMeterDebtEntity.setCurrentMaintenance(new BigDecimal("0.00"));
        accountMeterDebtEntity.setElevatorMaintenance(new BigDecimal("0.00"));
        accountMeterDebtEntity.setGarbageRemove(new BigDecimal("0.00"));
        accountMeterDebtEntity.setElectricity(new BigDecimal("0.00"));
        accountMeterDebtEntity.setColdWater(new BigDecimal("0.00"));
        accountMeterDebtEntity.setHotWater(new BigDecimal("0.00"));
        accountMeterDebtRepository.save(accountMeterDebtEntity);
        return accountMapperCustom.mapToDto(entity);
    }

    @Override
    @Transactional
    public MeterDto sendAccountMeter(MeterDto dto) {
        if(dto.getServiceId() < 5 || dto.getServiceId() > 7) {
            log.error("Wrong service type: {}",dto.getServiceId());
            throw new ServiceTypeException("Wrong service type for payment", dto.getServiceId());
        }

        dto.setDate(LocalDateTime.now());
        var accountEntity = accountRepository
                .findById(dto.getAccountId())
                .orElseThrow(()-> new RuntimeException("Account not found."));
        var serviceTariffValue = serviceTypeRepository
                .findById(dto.getServiceId())
                .orElseThrow(()-> new RuntimeException("Service type not found."));
        var accountDebtEntity = accountDebtRepository
                .findById(dto.getAccountId())
                .orElseThrow(()-> new RuntimeException("Account in debt table not found."));
        var accountMeterDebtEntity = accountMeterDebtRepository
                .findById(dto.getAccountId())
                .orElseThrow(()-> new RuntimeException("Account in meter debt table not found."));

        var result = new BigDecimal(serviceTariffValue.getTariff().toString());

        if(dto.getServiceId() > 4) {
            result = result.multiply(dto.getValue());
            log.info("RESULT: {} * {} = {}",
                    serviceTariffValue.getTariff().toString(),
                    dto.getValue(),
                    result);
        }

        accountMeterDebtEntity = getAccountMeterDebtValue(accountMeterDebtEntity, dto.getServiceId(), result);

        result = accountDebtEntity.getDebt().add(result);
        accountDebtEntity.setDebt(result);
        var entity = meterRepository
                .save(accountMapperCustom.mapToEntity(dto));
        accountDebtRepository.save(accountDebtEntity);
        accountMeterDebtRepository.save(accountMeterDebtEntity);
        return accountMapperCustom.mapToDto(entity);
    }

    @Override
    @Transactional
    public AccountAllDebtDto calculateAllDebt() {
        var accountMeterDebtEntitiesList = accountMeterDebtRepository.findAll();
        accountMeterDebtEntitiesList.forEach(this::calculateAccountMeterDebt);
        return calculateAllAccountsDebt();
    }

    @Override
    public AccountAllDebtDto showDebtInfo() {
        return calculateAllAccountsDebt();
    }

    @Override
    @Transactional
    public List<AccountDebtDto> getAllDebtors() {
        var debtorsList = new ArrayList<AccountDebtDto>();
        var accountDebtEntities = accountDebtRepository.findAll();
        for (var entity : accountDebtEntities) {
            if(entity.getDebt().doubleValue() > 0) {
                debtorsList.add(accountMapperCustom.mapToDto(entity));
            }
        }
        return debtorsList;
    }

    @Override
    public AccountMeterDebtDto getAccountMeterDebt(Integer accountId) {
        var accountDebtEntity = accountDebtRepository
                .findById(accountId)
                .orElseThrow(()-> new RuntimeException("Account in debt table not found."));
        if(accountDebtEntity.getDebt().doubleValue() <= 0) {
            return AccountMeterDebtDto.builder().build();
        }
        var accountMeterDebtEntity = accountMeterDebtRepository
                .findById(accountDebtEntity.getAccountId())
                .orElseThrow(()-> new RuntimeException("Account in meter debt table not found."));
        return accountMapperCustom.mapToDto(accountMeterDebtEntity);
    }


    @Transactional
    private AccountAllDebtDto calculateAllAccountsDebt() {
        var debtorsCounter = 0;
        var totalDebt = new BigDecimal("0.00");
        var accountDebtEntities = accountDebtRepository.findAll();
        for (var entity : accountDebtEntities) {
            if(entity.getDebt().doubleValue() > 0) {
                ++debtorsCounter;
                totalDebt = totalDebt.add(entity.getDebt());
            }
        }
        return AccountAllDebtDto.builder()
                .debtorsCounter(debtorsCounter)
                .totalDebt(totalDebt)
                .build();
    }

    @Transactional
    private void calculateAccountMeterDebt(AccountMeterDebtEntity meterDebtEntity) {
        var square = accountRepository
                .findById(meterDebtEntity.getId())
                .orElseThrow(()-> new RuntimeException("Account not found"))
                .getAddress()
                .getSquare();
        meterDebtEntity.setHouseMaintenance(
                meterDebtEntity.getHouseMaintenance()
                        .add(square
                                .multiply(serviceTypeRepository.findById(1).orElseThrow(()-> new RuntimeException("Service type not found."))
                                        .getTariff())));
        meterDebtEntity.setCurrentMaintenance(
                meterDebtEntity.getCurrentMaintenance()
                        .add(square
                                .multiply(serviceTypeRepository.findById(2).orElseThrow(()-> new RuntimeException("Service type not found."))
                                        .getTariff())));
        meterDebtEntity.setElevatorMaintenance(
                meterDebtEntity.getElevatorMaintenance()
                        .add(square
                                .multiply(serviceTypeRepository.findById(3).orElseThrow(()-> new RuntimeException("Service type not found."))
                                        .getTariff())));
        meterDebtEntity.setGarbageRemove(
                meterDebtEntity.getGarbageRemove()
                        .add(square
                                .multiply(serviceTypeRepository.findById(4).orElseThrow(()-> new RuntimeException("Service type not found."))
                                        .getTariff())));
        var accountCurrentDebtEntity = accountDebtRepository
                .findById(meterDebtEntity.getId())
                .orElseThrow(()-> new RuntimeException("Account in debt table not found"));
        var newDebt = accountCurrentDebtEntity.getDebt();
        newDebt = newDebt.add(meterDebtEntity.getHouseMaintenance()
                        .add(meterDebtEntity.getCurrentMaintenance()
                                .add(meterDebtEntity.getElevatorMaintenance()
                                        .add(meterDebtEntity.getGarbageRemove()))));
        accountCurrentDebtEntity.setDebt(newDebt);
        accountDebtRepository.save(accountCurrentDebtEntity);
    }

    private AccountMeterDebtEntity getAccountMeterDebtValue(AccountMeterDebtEntity entity, Integer serviceId, BigDecimal addedDebt) {

        var debtValue = new BigDecimal("0.00");
        switch (serviceId) {
            case 1 -> {
                debtValue = entity.getHouseMaintenance();
                entity.setHouseMaintenance(debtValue.add(addedDebt));
            }
            case 2 -> {
                debtValue = entity.getCurrentMaintenance();
                entity.setCurrentMaintenance(debtValue.add(addedDebt));
            }
            case 3 -> {
                debtValue = entity.getElevatorMaintenance();
                entity.setElevatorMaintenance(debtValue.add(addedDebt));
            }
            case 4 -> {
                debtValue = entity.getGarbageRemove();
                entity.setGarbageRemove(debtValue.add(addedDebt));
            }
            case 5 -> {
                debtValue = entity.getElectricity();
                entity.setElectricity(debtValue.add(addedDebt));
            }
            case 6 -> {
                debtValue = entity.getColdWater();
                entity.setColdWater(debtValue.add(addedDebt));
            }
            case 7 -> {
                debtValue = entity.getHotWater();
                entity.setHotWater(debtValue.add(addedDebt));
            }
        }
        return entity;
    }

}
