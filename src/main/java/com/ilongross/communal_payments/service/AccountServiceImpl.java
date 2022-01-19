package com.ilongross.communal_payments.service;

import com.ilongross.communal_payments.model.dto.AccountDto;
import com.ilongross.communal_payments.model.mapper.AccountMapperCustom;
import com.ilongross.communal_payments.repository.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapperCustom accountMapperCustom;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapperCustom accountMapperCustom) {
        this.accountRepository = accountRepository;
        this.accountMapperCustom = accountMapperCustom;
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
}
