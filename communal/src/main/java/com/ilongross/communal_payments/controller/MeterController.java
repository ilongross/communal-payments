package com.ilongross.communal_payments.controller;

import com.ilongross.communal_payments.model.dto.MeterDto;
import com.ilongross.communal_payments.service.AccountService;
import com.ilongross.communal_payments.service.MeterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/communal/meter")
@RequiredArgsConstructor
public class MeterController {

    private final MeterService meterService;
    private final AccountService accountService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String getMenu() {
        return "meter_menu";
    }

    @GetMapping("/send")
    @ResponseStatus(HttpStatus.OK)
    public String accountMeterForm(Model model) {
        model.addAttribute("meterDto", MeterDto.builder().build());
        return "meter_send_form";
    }


    @PostMapping("/send")
    @ResponseStatus(HttpStatus.CREATED)
    public String sendAccountMeter(@ModelAttribute MeterDto dto, Model model) {
        var meterResult = accountService.sendAccountMeter(dto);
        model.addAttribute("meterResult", meterResult);
        return "meter_send_result";
    }

}
