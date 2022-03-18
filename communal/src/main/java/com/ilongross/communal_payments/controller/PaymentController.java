package com.ilongross.communal_payments.controller;

import com.ilongross.communal_payments.model.dto.PaymentDto;
import com.ilongross.communal_payments.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/communal/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String makePayment(@NonNull @RequestBody PaymentDto dto, Model model) {
        var payment = paymentService.makePayment(dto);
        model.addAttribute("payment", payment);
        return "payment_result";
    }

    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public String getPaymentById(@PathVariable Integer id, Model model) {
        var payment = paymentService.findById(id);
        model.addAttribute("payment", payment);
        return "payment_info";
    }

}
