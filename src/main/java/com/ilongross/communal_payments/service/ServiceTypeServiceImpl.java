package com.ilongross.communal_payments.service;

import com.ilongross.communal_payments.model.dto.ServiceTypeDto;
import com.ilongross.communal_payments.model.mapper.ServiceTypeMapper;
import com.ilongross.communal_payments.repository.ServiceTypeRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ServiceTypeServiceImpl implements ServiceTypeService{

    private final ServiceTypeRepository serviceTypeRepository;
    private final ServiceTypeMapper serviceTypeMapper;

    public ServiceTypeServiceImpl(ServiceTypeRepository serviceTypeRepository, ServiceTypeMapper serviceTypeMapper) {
        this.serviceTypeRepository = serviceTypeRepository;
        this.serviceTypeMapper = serviceTypeMapper;
    }

    @Override
    public List<ServiceTypeDto> getAllServiceType() {
        return serviceTypeRepository.findAll().stream()
                .map(serviceTypeMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ServiceTypeDto getServiceTypeById(Integer id) {
        return serviceTypeMapper.mapToDto(
                serviceTypeRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Service type not found.")));
    }

    @Override
    public ServiceTypeDto saveNewServiceType(ServiceTypeDto dto) {
        var entity = serviceTypeRepository
                .save(serviceTypeMapper.mapToEntity(dto));
        return serviceTypeMapper.mapToDto(entity);
    }

    @Override
    @Transactional
    public ServiceTypeDto updateServiceTariff(ServiceTypeDto dto) {
        var entity = serviceTypeRepository
                .findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Service type not found"));
        entity.setTariff(dto.getTariff());
        entity = serviceTypeRepository.save(entity);
        return serviceTypeMapper.mapToDto(entity);
    }


}
