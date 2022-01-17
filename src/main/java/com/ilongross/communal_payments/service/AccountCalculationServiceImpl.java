package com.ilongross.communal_payments.service;

import com.ilongross.communal_payments.model.entity.AccountEntity;
import com.ilongross.communal_payments.repository.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountCalculationServiceImpl implements AccountCalculationService{

    private final AccountRepository accountRepository;

    public AccountCalculationServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<AccountEntity> getAllAccounts() {
        return accountRepository.findAll();
    }
}
