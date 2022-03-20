package com.ilongross.communal_payments.controller;

import com.ilongross.communal_payments.model.dto.PaymentDto;
import com.ilongross.communal_payments.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/communal/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String getMenu() {
        return "payment_menu";
    }

    @GetMapping("/send")
    @ResponseStatus(HttpStatus.OK)
    public String makePayment(Model model) {
        model.addAttribute("payment", PaymentDto.builder().build());
        return "payment_send_form";
    }

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String makePayment(@ModelAttribute PaymentDto dto, Model model) {
        var payment = paymentService.makePayment(dto);
        model.addAttribute("payment", payment);
        return "payment_result";
    }

}
