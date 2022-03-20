package com.ilongross.communal_payments.service;

import com.ilongross.communal_payments.model.dto.AddressDto;

import java.util.List;

public interface AddressService {

    AddressDto getAddressById(Integer id);
    List<AddressDto> getAll();
    AddressDto create(AddressDto addressDto);
}
