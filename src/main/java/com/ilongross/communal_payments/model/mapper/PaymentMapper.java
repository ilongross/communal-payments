package com.ilongross.communal_payments.model.mapper;

import com.ilongross.communal_payments.model.dto.PaymentDto;
import com.ilongross.communal_payments.model.dto.PaymentResultDto;
import com.ilongross.communal_payments.model.entity.PaymentEntity;
import com.ilongross.communal_payments.repository.AccountRepository;
import com.ilongross.communal_payments.repository.ServiceTypeRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class PaymentMapper {

    private final AccountRepository accountRepository;
    private final ServiceTypeRepository serviceTypeRepository;

    public PaymentMapper(AccountRepository accountRepository, ServiceTypeRepository serviceTypeRepository) {
        this.accountRepository = accountRepository;
        this.serviceTypeRepository = serviceTypeRepository;
    }

    public PaymentDto mapToDto(PaymentEntity entity){
        return PaymentDto.builder()
                .id(entity.getId())
                .accountId(entity.getAccountId().getId())
                .serviceId(entity.getServiceId().getId())
                .sum(new BigDecimal(entity.getSum().toString()))
                .build();
    }

    public PaymentEntity mapToEntity(PaymentDto dto) {
        var entity = new PaymentEntity();
        entity.setAccountId(accountRepository.getById(dto.getAccountId()));
        entity.setServiceId(serviceTypeRepository.getById(dto.getServiceId()));
        entity.setSum(new BigDecimal(dto.getSum().toString()));
        entity.setDate(LocalDateTime.now());
        return entity;
    }

    public PaymentResultDto mapToResultDto(PaymentDto dto) {
        return mapToResultDto(mapToEntity(dto));
    }

    public PaymentResultDto mapToResultDto(PaymentEntity entity) {
        var addressEntity = entity.getAccountId().getAddress();
        var addressString = String.format(
                "%s, %s, %s, %s, %s, %.2f",
                addressEntity.getRegion(),
                addressEntity.getCity(),
                addressEntity.getStreet(),
                addressEntity.getHouse(),
                addressEntity.getApartment(),
                addressEntity.getSquare().doubleValue());
        var accountEntity = entity.getAccountId();
        var initialsString = String.format(
                "%s %s %s",
                accountEntity.getLastname(),
                accountEntity.getName(),
                accountEntity.getPatronymic());
        return PaymentResultDto.builder()
                .address(addressString)
                .accountInitials(initialsString)
                .serviceName(entity.getServiceId().getServiceName())
                .sum(new BigDecimal(entity.getSum().toString()))
                .date(entity.getDate())
                .build();
    }


}
