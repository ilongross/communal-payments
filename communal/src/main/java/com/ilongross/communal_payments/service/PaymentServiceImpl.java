package com.ilongross.communal_payments.service;

import com.ilongross.communal_payments.exception.AccountIdNotFoundException;
import com.ilongross.communal_payments.exception.IdNotFoundException;
import com.ilongross.communal_payments.model.dto.PaymentResultDto;
import com.ilongross.communal_payments.model.dto.PaymentDto;
import com.ilongross.communal_payments.model.entity.AccountMeterDebtEntity;
import com.ilongross.communal_payments.model.mapper.PaymentMapper;
import com.ilongross.communal_payments.repository.AccountDebtRepository;
import com.ilongross.communal_payments.repository.AccountMeterDebtRepository;
import com.ilongross.communal_payments.repository.AccountRepository;
import com.ilongross.communal_payments.repository.PaymentRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final AccountDebtRepository accountDebtRepository;
    private final PaymentMapper paymentMapper;
    private final AccountRepository accountRepository;
    private final AccountMeterDebtRepository accountMeterDebtRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              AccountDebtRepository accountDebtRepository,
                              PaymentMapper paymentMapper, AccountRepository accountRepository, AccountMeterDebtRepository accountMeterDebtRepository) {
        this.paymentRepository = paymentRepository;
        this.accountDebtRepository = accountDebtRepository;
        this.paymentMapper = paymentMapper;
        this.accountRepository = accountRepository;
        this.accountMeterDebtRepository = accountMeterDebtRepository;
    }

    @Override
    @Transactional
    public PaymentResultDto makePayment(PaymentDto dto) {
        var account = accountRepository
                .findById(dto.getAccountId())
                .orElseThrow(()-> new AccountIdNotFoundException(dto.getAccountId()));
        var accountDebtEntity = accountDebtRepository
                .findById(account.getId())
                .orElseThrow(()-> new IdNotFoundException(account.getId()));
        var accountMeterDebtEntity = accountMeterDebtRepository
                .findById(dto.getAccountId())
                .orElseThrow(()-> new IdNotFoundException(dto.getAccountId()));
        var paymentResultDto = paymentMapper.mapToResultDto(
                paymentRepository.save(
                        paymentMapper.mapToEntity(dto)));

        accountMeterDebtEntity = getAccountMeterDebtValue(accountMeterDebtEntity, dto.getServiceId(), dto.getSum());
        accountMeterDebtRepository.save(accountMeterDebtEntity);

        var currentDebt = accountDebtEntity.getDebt();
        accountDebtEntity.setDebt(currentDebt.subtract(dto.getSum()));
        accountDebtRepository.save(accountDebtEntity);
        return paymentResultDto;
    }

    private AccountMeterDebtEntity getAccountMeterDebtValue(AccountMeterDebtEntity entity, Integer serviceId, BigDecimal addedDebt) {

        var debtValue = new BigDecimal("0.00");
        switch (serviceId) {
            case 1 -> {
                debtValue = entity.getHouseMaintenance();
                entity.setHouseMaintenance(debtValue.subtract(addedDebt));
            }
            case 2 -> {
                debtValue = entity.getCurrentMaintenance();
                entity.setCurrentMaintenance(debtValue.subtract(addedDebt));
            }
            case 3 -> {
                debtValue = entity.getElevatorMaintenance();
                entity.setElevatorMaintenance(debtValue.subtract(addedDebt));
            }
            case 4 -> {
                debtValue = entity.getGarbageRemove();
                entity.setGarbageRemove(debtValue.subtract(addedDebt));
            }
            case 5 -> {
                debtValue = entity.getElectricity();
                entity.setElectricity(debtValue.subtract(addedDebt));
            }
            case 6 -> {
                debtValue = entity.getColdWater();
                entity.setColdWater(debtValue.subtract(addedDebt));
            }
            case 7 -> {
                debtValue = entity.getHotWater();
                entity.setHotWater(debtValue.subtract(addedDebt));
            }
        }
        return entity;
    }


}