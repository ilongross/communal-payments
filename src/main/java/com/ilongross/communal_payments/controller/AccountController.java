package com.ilongross.communal_payments.controller;


import com.ilongross.communal_payments.model.dto.*;
import com.ilongross.communal_payments.model.entity.AccountMeterDebtEntity;
import com.ilongross.communal_payments.service.AccountService;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/communal/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(accountService.getAllAccounts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Integer id) {
        return ResponseEntity
                .ok()
                .body(accountService.getAccountById(id));
    }

    @GetMapping("/debt/{id}")
    public ResponseEntity<AccountDebtDto> getAccountDebtByAccountId(@PathVariable Integer id) {
        return ResponseEntity
                .ok()
                .body(accountService.getAccountDebtByAccountId(id));
    }

    @PostMapping("/create")
    public ResponseEntity<AccountDto> createNewAccount(@NotNull @RequestBody AccountDto dto) {
        return ResponseEntity
                .ok()
                .body(accountService.createNewAccount(dto));
    }

    @PostMapping("/send_meter")
    public ResponseEntity<MeterDto> sendAccountMeter(@NotNull @RequestBody MeterDto dto) {
        return ResponseEntity
                .ok()
                .body(accountService.sendAccountMeter(dto));
    }

    @GetMapping("/calculate_all")
    public ResponseEntity<AccountAllDebtDto> calculateAllDebt() {
        return ResponseEntity
                .ok()
                .body(accountService.calculateAllDebt());
    }

    @GetMapping("/debt_info")
    public ResponseEntity<AccountAllDebtDto> showDebtInfo() {
        return ResponseEntity
                .ok()
                .body(accountService.showDebtInfo());
    }

    @GetMapping("/meter_debt/{id}")
    public ResponseEntity<AccountMeterDebtDto> getAccountMeterDebt(@PathVariable Integer id) {
        return ResponseEntity
                .ok()
                .body(accountService.getAccountMeterDebt(id));
    }

    @GetMapping("/debtors")
    public ResponseEntity<List<AccountDebtDto>> getAllDebtors() {
        return ResponseEntity
                .ok()
                .body(accountService.getAllDebtors());
    }

    @GetMapping("/report/{id}")
    public ResponseEntity<AccountReportDto> getAccountReportByPeriod(@PathVariable Integer id,
                                                                     @RequestBody DatePeriodDto period) {
        return ResponseEntity
                .ok()
                .body(accountService.getAccountReport(id, period));
    }


}
