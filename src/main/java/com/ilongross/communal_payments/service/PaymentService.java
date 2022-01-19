package com.ilongross.communal_payments.service;

import com.ilongross.communal_payments.model.dto.PaymentResultDto;
import com.ilongross.communal_payments.model.dto.PaymentDto;
import org.springframework.stereotype.Component;

@Component
public interface PaymentService {

    PaymentResultDto makePayment(PaymentDto dto);


}
