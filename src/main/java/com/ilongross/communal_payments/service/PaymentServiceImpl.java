package com.ilongross.communal_payments.service;

import com.ilongross.communal_payments.model.dto.PaymentResultDto;
import com.ilongross.communal_payments.model.dto.PaymentDto;
import com.ilongross.communal_payments.model.mapper.PaymentMapper;
import com.ilongross.communal_payments.repository.PaymentRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    @Transactional
    public PaymentResultDto makePayment(PaymentDto dto) {
        return paymentMapper.mapToResultDto(dto);
    }
}