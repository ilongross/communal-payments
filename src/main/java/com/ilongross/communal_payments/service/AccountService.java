package com.ilongross.communal_payments.service;

import com.ilongross.communal_payments.model.dto.AccountCreateDto;
import com.ilongross.communal_payments.model.dto.AccountDebtDto;
import com.ilongross.communal_payments.model.dto.AccountDto;
import com.ilongross.communal_payments.model.dto.MeterDto;
import com.ilongross.communal_payments.model.entity.AccountEntity;

import java.util.List;

public interface AccountService {

    List<AccountDto> getAllAccounts();
    AccountDto getAccountById(Integer accountId);
    AccountDebtDto getAccountDebtByAccountId(Integer accountId);
    AccountDto createNewAccount(AccountDto dto);
    MeterDto sendAccountMeter(MeterDto dto);
}
