package com.ilongross.communal_payments.service;

import com.ilongross.communal_payments.exception.AccountIdNotFoundException;
import com.ilongross.communal_payments.exception.ServiceTypeException;
import com.ilongross.communal_payments.model.dto.*;
import com.ilongross.communal_payments.model.entity.AccountDebtEntity;
import com.ilongross.communal_payments.model.entity.AccountMeterDebtEntity;
import com.ilongross.communal_payments.model.mapper.AccountMapperCustom;
import com.ilongross.communal_payments.model.mapper.PaymentMapper;
import com.ilongross.communal_payments.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapperCustom accountMapperCustom;
    private final AccountDebtRepository accountDebtRepository;
    private final MeterRepository meterRepository;
    private final ServiceTypeRepository serviceTypeRepository;
    private final AccountMeterDebtRepository accountMeterDebtRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(accountMapperCustom::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto getAccountById(Integer accountId) {
        return accountMapperCustom
                .mapToDto(accountRepository.findById(accountId)
                        .orElseThrow(() -> new AccountIdNotFoundException(accountId)));
    }

    @Override
    public AccountDebtDto getAccountDebtByAccountId(Integer accountId) {
        return accountMapperCustom
                .mapToDto(
                        accountDebtRepository
                                .findById(accountId)
                                .orElseThrow(() -> new AccountIdNotFoundException(accountId)));
    }

    @Override
    @Transactional
    public AccountDto createNewAccount(AccountDto dto) {
        var entity = accountRepository
                .save(accountMapperCustom.mapToEntity(dto));
        var accountDebtEntity = new AccountDebtEntity();
        accountDebtEntity.setDebt(new BigDecimal("0.00"));
        accountDebtRepository.save(accountDebtEntity);
        var accountMeterDebtEntity = new AccountMeterDebtEntity();
        accountMeterDebtEntity.setHouseMaintenance(new BigDecimal("0.00"));
        accountMeterDebtEntity.setCurrentMaintenance(new BigDecimal("0.00"));
        accountMeterDebtEntity.setElevatorMaintenance(new BigDecimal("0.00"));
        accountMeterDebtEntity.setGarbageRemove(new BigDecimal("0.00"));
        accountMeterDebtEntity.setElectricity(new BigDecimal("0.00"));
        accountMeterDebtEntity.setColdWater(new BigDecimal("0.00"));
        accountMeterDebtEntity.setHotWater(new BigDecimal("0.00"));
        accountMeterDebtRepository.save(accountMeterDebtEntity);
        return accountMapperCustom.mapToDto(entity);
    }

    @Override
    @Transactional
    public MeterResultDto sendAccountMeter(MeterDto dto) {
        if(dto.getServiceId() < 5 || dto.getServiceId() > 7) {
            throw new ServiceTypeException(dto.getServiceId());
        }
        dto.setDate(LocalDateTime.now());
        var accountEntity = accountRepository
                .findById(dto.getAccountId())
                .orElseThrow(()-> new AccountIdNotFoundException(dto.getAccountId()));
        var serviceTariffValue = serviceTypeRepository
                .findById(dto.getServiceId())
                .orElseThrow(()-> new ServiceTypeException(dto.getServiceId()));
        var accountDebtEntity = accountDebtRepository
                .findById(dto.getAccountId())
                .orElseThrow(()-> new AccountIdNotFoundException(dto.getAccountId()));
        var accountMeterDebtEntity = accountMeterDebtRepository
                .findById(dto.getAccountId())
                .orElseThrow(()-> new AccountIdNotFoundException(dto.getAccountId()));

        var result = new BigDecimal(serviceTariffValue.getTariff().toString());

        if(dto.getServiceId() > 4) {
            result = result.multiply(dto.getValue());
            log.info("RESULT: {} * {} = {}",
                    serviceTariffValue.getTariff().toString(),
                    dto.getValue(),
                    result);
        }

        accountMeterDebtEntity = getAccountMeterDebtValue(accountMeterDebtEntity, dto.getServiceId(), result);

        result = accountDebtEntity.getDebt().add(result);
        accountDebtEntity.setDebt(result);
        var entity = meterRepository
                .save(accountMapperCustom.mapToEntity(dto));
        accountDebtRepository.save(accountDebtEntity);
        accountMeterDebtRepository.save(accountMeterDebtEntity);
        return accountMapperCustom.mapToMeterResultDto(entity);
    }

    @Override
    @Transactional
    public AccountAllDebtDto calculateAllDebt() {
        var accountMeterDebtEntitiesList = accountMeterDebtRepository.findAll();
        accountMeterDebtEntitiesList.forEach(this::calculateAccountMeterDebt);
        return calculateAllAccountsDebt();
    }

    @Override
    public AccountAllDebtDto showDebtInfo() {
        return calculateAllAccountsDebt();
    }

    @Override
    @Transactional
    public List<AccountDebtDto> getAllDebtors() {
        var debtorsList = new ArrayList<AccountDebtDto>();
        var accountDebtEntities = accountDebtRepository.findAll();
        for (var entity : accountDebtEntities) {
            if(entity.getDebt().doubleValue() > 0) {
                debtorsList.add(accountMapperCustom.mapToDto(entity));
            }
        }
        return debtorsList;
    }

    @Override
    public AccountMeterDebtDto getAccountMeterDebt(Integer accountId) {
        var accountDebtEntity = accountDebtRepository
                .findById(accountId)
                .orElseThrow(()-> new AccountIdNotFoundException(accountId));
        if(accountDebtEntity.getDebt().doubleValue() <= 0) {
            return AccountMeterDebtDto.builder().build();
        }
        var accountMeterDebtEntity = accountMeterDebtRepository
                .findById(accountDebtEntity.getAccountId())
                .orElseThrow(()-> new AccountIdNotFoundException(accountDebtEntity.getAccountId()));
        return accountMapperCustom.mapToDto(accountMeterDebtEntity);
    }

    @Override
    public AccountReportDto getAccountReport(Integer accountId, DatePeriodDto periodDto) {

        var accountEntity = accountRepository.findById(accountId)
                .orElseThrow(()-> new AccountIdNotFoundException(accountId));
        var accountMeterDtoByPeriodMap = meterRepository
                .findByAccountId(accountEntity).stream()
                .filter(e -> e.getDate().compareTo(periodDto.getStartDate()) >= 0 &&
                        e.getDate().compareTo(periodDto.getEndDate()) <= 0)
                .map(accountMapperCustom::mapToDto)
                .collect(Collectors.groupingBy(MeterDto::getServiceId));
        var accountPaymentDtoByPeriodMap = paymentRepository
                .findByAccountId(accountEntity).stream()
                .filter(e -> e.getDate().compareTo(periodDto.getStartDate()) >= 0 &&
                        e.getDate().compareTo(periodDto.getEndDate()) <= 0)
                .map(paymentMapper::mapToDto)
                .collect(Collectors.groupingBy(PaymentDto::getServiceId));

        var serviceIdSet = new HashSet<Integer>();
        serviceIdSet.addAll(new ArrayList<>(accountMeterDtoByPeriodMap.keySet()));
        serviceIdSet.addAll(new ArrayList<>(accountPaymentDtoByPeriodMap.keySet()));

        var meterReportDtoList = new ArrayList<MeterReportDto>();
        for (var serviceId : serviceIdSet) {
            var meterReportDto = MeterReportDto.builder().build();
            var serviceEntity = serviceTypeRepository
                    .findById(serviceId)
                    .orElseThrow(()-> new ServiceTypeException(serviceId));
            meterReportDto.setServiceName(serviceEntity.getServiceName());
            meterReportDto.setAddedSum(new BigDecimal("0.00"));
            meterReportDto.setPaidSum(new BigDecimal("0.00"));

            var addedSumCurrent = meterReportDto.getAddedSum();
            if(accountMeterDtoByPeriodMap.get(serviceId) != null) {
                for (var meter : accountMeterDtoByPeriodMap.get(serviceId)) {
                    addedSumCurrent = addedSumCurrent.add(meter.getValue().multiply(serviceEntity.getTariff()));
                }
            }
            meterReportDto.setAddedSum(addedSumCurrent);
            var paidSumCurrent = meterReportDto.getPaidSum();

            if(accountPaymentDtoByPeriodMap.get(serviceId) != null) {
                for (var payment : accountPaymentDtoByPeriodMap.get(serviceEntity.getId())) {
                    paidSumCurrent = paidSumCurrent.add(payment.getSum());
                }
            }
            meterReportDto.setPaidSum(paidSumCurrent);
            meterReportDto.setCurrentDebt(getServiceDebtValue(accountId, serviceId));
            meterReportDtoList.add(meterReportDto);
        }

        return AccountReportDto
                .builder()
                .accountId(accountId)
                .period(periodDto)
                .meterReportList(meterReportDtoList)
                .build();
    }

    @Override
    public void delete(Integer id) {
        accountRepository.deleteById(id);
    }


    @Transactional
    AccountAllDebtDto calculateAllAccountsDebt() {
        var debtorsCounter = 0;
        var totalDebt = new BigDecimal("0.00");
        var accountDebtEntities = accountDebtRepository.findAll();
        for (var entity : accountDebtEntities) {
            if(entity.getDebt().doubleValue() > 0) {
                ++debtorsCounter;
                totalDebt = totalDebt.add(entity.getDebt());
            }
        }
        return AccountAllDebtDto.builder()
                .debtorsCounter(debtorsCounter)
                .totalDebt(totalDebt)
                .build();
    }

    @Transactional
    void calculateAccountMeterDebt(AccountMeterDebtEntity meterDebtEntity) {
        var square = accountRepository
                .findById(meterDebtEntity.getId())
                .orElseThrow(()-> new AccountIdNotFoundException(meterDebtEntity.getId()))
                .getAddress()
                .getSquare();
        meterDebtEntity.setHouseMaintenance(
                meterDebtEntity.getHouseMaintenance()
                        .add(square
                                .multiply(serviceTypeRepository.findById(1).orElseThrow(()-> new ServiceTypeException(1))
                                        .getTariff())));
        meterDebtEntity.setCurrentMaintenance(
                meterDebtEntity.getCurrentMaintenance()
                        .add(square
                                .multiply(serviceTypeRepository.findById(2).orElseThrow(()-> new ServiceTypeException(2))
                                        .getTariff())));
        meterDebtEntity.setElevatorMaintenance(
                meterDebtEntity.getElevatorMaintenance()
                        .add(square
                                .multiply(serviceTypeRepository.findById(3).orElseThrow(()-> new ServiceTypeException(3))
                                        .getTariff())));
        meterDebtEntity.setGarbageRemove(
                meterDebtEntity.getGarbageRemove()
                        .add(square
                                .multiply(serviceTypeRepository.findById(4).orElseThrow(()-> new ServiceTypeException(4))
                                        .getTariff())));
        var accountCurrentDebtEntity = accountDebtRepository
                .findById(meterDebtEntity.getId())
                .orElseThrow(()-> new AccountIdNotFoundException(meterDebtEntity.getId()));
        var newDebt = accountCurrentDebtEntity.getDebt();
        newDebt = newDebt.add(meterDebtEntity.getHouseMaintenance()
                        .add(meterDebtEntity.getCurrentMaintenance()
                                .add(meterDebtEntity.getElevatorMaintenance()
                                        .add(meterDebtEntity.getGarbageRemove()))));
        accountCurrentDebtEntity.setDebt(newDebt);
        accountDebtRepository.save(accountCurrentDebtEntity);
    }

    private BigDecimal getServiceDebtValue(Integer accountId, Integer serviceId) {
        var entity = accountMeterDebtRepository
                .findById(accountId)
                .orElseThrow(() -> new AccountIdNotFoundException(accountId));
        var debtValue = new BigDecimal("0.00");
        switch (serviceId) {
            case 1 -> debtValue = entity.getHouseMaintenance();
            case 2 -> debtValue = entity.getCurrentMaintenance();
            case 3 -> debtValue = entity.getElevatorMaintenance();
            case 4 -> debtValue = entity.getGarbageRemove();
            case 5 -> debtValue = entity.getElectricity();
            case 6 -> debtValue = entity.getColdWater();
            case 7 -> debtValue = entity.getHotWater();
        }
        return debtValue;
    }

    private AccountMeterDebtEntity getAccountMeterDebtValue(AccountMeterDebtEntity entity, Integer serviceId, BigDecimal addedDebt) {

        var debtValue = new BigDecimal("0.00");
        switch (serviceId) {
            case 1 -> {
                debtValue = entity.getHouseMaintenance();
                entity.setHouseMaintenance(debtValue.add(addedDebt));
            }
            case 2 -> {
                debtValue = entity.getCurrentMaintenance();
                entity.setCurrentMaintenance(debtValue.add(addedDebt));
            }
            case 3 -> {
                debtValue = entity.getElevatorMaintenance();
                entity.setElevatorMaintenance(debtValue.add(addedDebt));
            }
            case 4 -> {
                debtValue = entity.getGarbageRemove();
                entity.setGarbageRemove(debtValue.add(addedDebt));
            }
            case 5 -> {
                debtValue = entity.getElectricity();
                entity.setElectricity(debtValue.add(addedDebt));
            }
            case 6 -> {
                debtValue = entity.getColdWater();
                entity.setColdWater(debtValue.add(addedDebt));
            }
            case 7 -> {
                debtValue = entity.getHotWater();
                entity.setHotWater(debtValue.add(addedDebt));
            }
        }
        return entity;
    }

}
