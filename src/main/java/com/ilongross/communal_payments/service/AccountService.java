package com.ilongross.communal_payments.service;

import com.ilongross.communal_payments.model.dto.*;

import java.util.List;

public interface AccountService {

    List<AccountDto> getAllAccounts();
    AccountDto getAccountById(Integer accountId);
    AccountDebtDto getAccountDebtByAccountId(Integer accountId);
    AccountDto createNewAccount(AccountDto dto);
    MeterDto sendAccountMeter(MeterDto dto);
    AccountAllDebtDto calculateAllDebt();
    AccountAllDebtDto showDebtInfo();
    List<AccountDebtDto> getAllDebtors();
    AccountMeterDebtDto getAccountMeterDebt(Integer accountId);
}
