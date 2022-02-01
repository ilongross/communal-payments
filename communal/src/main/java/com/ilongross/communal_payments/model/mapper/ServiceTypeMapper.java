package com.ilongross.communal_payments.model.mapper;

import com.ilongross.communal_payments.model.dto.ServiceTypeDto;
import com.ilongross.communal_payments.model.entity.ServiceTypeEntity;
import com.ilongross.communal_payments.repository.ServiceTypeRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ServiceTypeMapper {

    private final ServiceTypeRepository serviceTypeRepository;

    public ServiceTypeMapper(ServiceTypeRepository serviceTypeRepository) {
        this.serviceTypeRepository = serviceTypeRepository;
    }

    public ServiceTypeDto mapToDto(ServiceTypeEntity entity) {
        return ServiceTypeDto.builder()
                .id(entity.getId())
                .serviceName(entity.getServiceName())
                .tariff(new BigDecimal(entity.getTariff().toString()))
                .build();
    }

    public ServiceTypeEntity mapToEntity(ServiceTypeDto dto) {
        var entity = new ServiceTypeEntity();
        entity.setId(dto.getId());
        entity.setServiceName(dto.getServiceName());
        entity.setTariff(new BigDecimal(dto.getTariff().toString()));
        return entity;
    }

}
