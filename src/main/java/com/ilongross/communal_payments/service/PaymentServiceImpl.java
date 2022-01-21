package com.ilongross.communal_payments.service;

import com.ilongross.communal_payments.model.dto.PaymentResultDto;
import com.ilongross.communal_payments.model.dto.PaymentDto;
import com.ilongross.communal_payments.model.mapper.PaymentMapper;
import com.ilongross.communal_payments.repository.AccountDebtRepository;
import com.ilongross.communal_payments.repository.AccountRepository;
import com.ilongross.communal_payments.repository.PaymentRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final AccountDebtRepository accountDebtRepository;
    private final PaymentMapper paymentMapper;
    private final AccountRepository accountRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              AccountDebtRepository accountDebtRepository,
                              PaymentMapper paymentMapper, AccountRepository accountRepository) {
        this.paymentRepository = paymentRepository;
        this.accountDebtRepository = accountDebtRepository;
        this.paymentMapper = paymentMapper;
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public PaymentResultDto makePayment(PaymentDto dto) {
        var account = accountRepository
                .findById(dto.getAccountId())
                .orElseThrow(()-> new RuntimeException("Account not found"));
        var accountDebtEntity = accountDebtRepository
                .findById(account.getId())
                .orElseThrow(()-> new RuntimeException("Account in debt table not found."));
        var paymentResultDto = paymentMapper.mapToResultDto(
                paymentRepository.save(
                        paymentMapper.mapToEntity(dto)));
        var currentDebt = accountDebtEntity.getDebt();
        accountDebtEntity.setDebt(currentDebt.subtract(dto.getSum()));
        accountDebtRepository.save(accountDebtEntity);
        return paymentResultDto;
    }
}