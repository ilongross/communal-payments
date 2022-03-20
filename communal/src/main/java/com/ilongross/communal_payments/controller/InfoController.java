package com.ilongross.communal_payments.controller;

import com.ilongross.communal_payments.model.dto.DateDto;
import com.ilongross.communal_payments.model.dto.DatePeriodDto;
import com.ilongross.communal_payments.model.dto.IntValue;
import com.ilongross.communal_payments.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/communal/info")
@RequiredArgsConstructor
public class InfoController {

    private final AccountService accountService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String getMenu(Model model) {
        model.addAttribute("id", IntValue.builder().build());
        model.addAttribute("period", DateDto.builder().build());
        return "info_menu";
    }

    @GetMapping("/account_debt")
    @ResponseStatus(HttpStatus.FOUND)
    public String getAccountDebtByAccountId(@ModelAttribute IntValue id, Model model) {
        var debt = accountService.getAccountDebtByAccountId(id.getValue());
        model.addAttribute("debt", debt);
        return "debt";
    }

    @GetMapping("/calculate_all")
    @ResponseStatus(HttpStatus.OK)
    public String calculateAllDebt(Model model) {
        var debtInfo = accountService.calculateAllDebt();
        var debtorsList = accountService.getAllDebtors();
        model.addAttribute("debtInfo", debtInfo);
        model.addAttribute("debtorsList", debtorsList);
        return "debt_info";
    }

    @GetMapping("/total_debt")
    @ResponseStatus(HttpStatus.OK)
    public String showDebtInfo(Model model) {
        var debtInfo = accountService.showDebtInfo();
        var debtorsList = accountService.getAllDebtors();
        model.addAttribute("debtInfo", debtInfo);
        model.addAttribute("debtorsList", debtorsList);
        return "debt_info";
    }

    @GetMapping("/report")
    @ResponseStatus(HttpStatus.OK)
    public String getAccountReportForm(Model model) {
        model.addAttribute("period", DateDto.builder().build());
        return "report_form";
    }

    @PostMapping("/report")
    @ResponseStatus(HttpStatus.FOUND)
    public String getAccountReportByPeriod(@ModelAttribute DateDto period,
                                           Model model) {
        var periodDto = DatePeriodDto.builder()
                .accountId(period.getAccountId())
                .startDate(LocalDateTime.parse(period.getStartDate()))
                .endDate(LocalDateTime.parse(period.getEndDate().split("T")[0] + "T23:59:59.999999"))
                .build();
        var accountReport = accountService.getAccountReport(periodDto.getAccountId(), periodDto);
        var meterReportList = accountReport.getMeterReportList();
        model.addAttribute("periodDto", periodDto);
        model.addAttribute("meterReportList", meterReportList);
        return "report_account";
    }


}
