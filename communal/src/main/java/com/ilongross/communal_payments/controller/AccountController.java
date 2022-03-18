package com.ilongross.communal_payments.controller;


import com.ilongross.communal_payments.model.dto.*;
import com.ilongross.communal_payments.service.AccountService;
import com.ilongross.communal_payments.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/communal/account")
@Slf4j
public class AccountController {

    private final AccountService accountService;
    private final AddressService addressService;

    //TODO сделать красивую таблицу
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.FOUND)
    public String getAllAccounts(Model model) {
        var accountsList = accountService.getAllAccounts();
        model.addAttribute("accountsList", accountsList);
        return "accounts_list";
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public String getAccountById(@PathVariable Integer id, Model model) {
        var account = accountService.getAccountById(id);
        var address = addressService.getAddressById(account.getAddress());
        model.addAttribute("account", account);
        model.addAttribute("address", address);
        return "account";
    }

    @GetMapping("/create")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String createAccountForm(Model model) {
        var acc = AccountDto.builder().build();
        model.addAttribute("account", acc);
        return "create_account_form";
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String createNewAccount(@ModelAttribute AccountDto acc, Model model) {
        var account = accountService.createNewAccount(acc);
        var address = addressService.getAddressById(account.getAddress());
        model.addAttribute("account", account);
        model.addAttribute("address", address);
        return "account";
    }

}
