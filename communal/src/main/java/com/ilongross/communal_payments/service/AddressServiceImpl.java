package com.ilongross.communal_payments.service;

import com.ilongross.communal_payments.exception.IdNotFoundException;
import com.ilongross.communal_payments.model.dto.AddressDto;
import com.ilongross.communal_payments.model.mapper.AddressMapper;
import com.ilongross.communal_payments.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService{

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Override
    public AddressDto getAddressById(Integer id) {
        var entity = addressRepository
                .findById(id)
                .orElseThrow(()-> new IdNotFoundException(id));
        return addressMapper.mapToDto(entity);
    }

    @Override
    public List<AddressDto> getAll() {
        var list = addressRepository.findAll().stream()
                .map(addressMapper::mapToDto).toList();
        return list;
    }

    @Override
    public AddressDto create(AddressDto addressDto) {
        var entity = addressRepository.save(addressMapper.mapToEntity(addressDto));
        return addressMapper.mapToDto(entity);
    }
}
