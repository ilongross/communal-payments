package com.ilongross.communal_payments.controller;

import com.ilongross.communal_payments.model.dto.PaymentResultDto;
import com.ilongross.communal_payments.model.dto.PaymentDto;
import com.ilongross.communal_payments.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/communal/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResultDto> makePayment(@NonNull @RequestBody PaymentDto dto) {
        return ResponseEntity
                .ok()
                .body(paymentService.makePayment(dto));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable Integer id) {
        return null;
    }

}
