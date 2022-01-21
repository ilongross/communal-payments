package com.ilongross.communal_payments.service;

import com.ilongross.communal_payments.model.dto.AccountDebtDto;
import com.ilongross.communal_payments.model.dto.AccountDto;
import com.ilongross.communal_payments.model.dto.MeterDto;
import com.ilongross.communal_payments.model.entity.AccountDebtEntity;
import com.ilongross.communal_payments.model.mapper.AccountMapperCustom;
import com.ilongross.communal_payments.repository.AccountDebtRepository;
import com.ilongross.communal_payments.repository.AccountRepository;
import com.ilongross.communal_payments.repository.MeterRepository;
import com.ilongross.communal_payments.repository.ServiceTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapperCustom accountMapperCustom, AccountDebtRepository accountDebtRepository, MeterRepository meterRepository, ServiceTypeRepository serviceTypeRepository) {
        this.accountRepository = accountRepository;
        this.accountMapperCustom = accountMapperCustom;
        this.accountDebtRepository = accountDebtRepository;
        this.meterRepository = meterRepository;
        this.serviceTypeRepository = serviceTypeRepository;
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
        accountDebtEntity.setAccountId(entity.getId());
        accountDebtEntity.setDebt(new BigDecimal("0.00"));
        accountDebtRepository.save(accountDebtEntity);
        return accountMapperCustom.mapToDto(entity);
    }

    @Override
    @Transactional
    public MeterDto sendAccountMeter(MeterDto dto) {
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
        var result = new BigDecimal(serviceTariffValue.getTariff().toString());
        if(dto.getServiceId() < 5) {
            //TODO сделать исключение
            result = result.multiply(accountEntity.getAddress().getSquare());
            log.info("RESULT: {} * {} = {}",
                    serviceTariffValue.getTariff().toString(),
                    accountEntity.getAddress().getSquare(),
                    result);
        } else {
            result = result.multiply(dto.getValue());
            log.info("RESULT: {} * {} = {}",
                    serviceTariffValue.getTariff().toString(),
                    dto.getValue(),
                    result);
        }
        result = accountDebtEntity.getDebt().add(result);
        accountDebtEntity.setDebt(result);
        var entity = meterRepository
                .save(accountMapperCustom.mapToEntity(dto));
        accountDebtRepository.save(accountDebtEntity);
        return accountMapperCustom.mapToDto(entity);
    }


}
