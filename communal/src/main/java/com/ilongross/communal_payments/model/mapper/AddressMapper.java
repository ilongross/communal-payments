package com.ilongross.communal_payments.model.mapper;

import com.ilongross.communal_payments.model.dto.AddressDto;
import com.ilongross.communal_payments.model.entity.AddressEntity;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public AddressDto mapToDto(AddressEntity entity) {
        var addressString = String.format(
                "регион %s, г.%s, ул.%s, д.:%s, кв.:%s, пл.(кв.м.):%.2f",
                entity.getRegion(),
                entity.getCity(),
                entity.getStreet(),
                entity.getHouse(),
                entity.getApartment(),
                entity.getSquare().doubleValue());
        return AddressDto.builder()
                .id(entity.getId())
                .region(entity.getRegion())
                .city(entity.getCity())
                .street(entity.getStreet())
                .house(entity.getHouse())
                .apartment(entity.getApartment())
                .square(entity.getSquare())
                .inLine(addressString)
                .build();
    }

    public AddressEntity mapToEntity(AddressDto addressDto) {
        var entity = new AddressEntity();
        entity.setRegion(addressDto.getRegion());
        entity.setCity(addressDto.getCity());
        entity.setStreet(addressDto.getStreet());
        entity.setHouse(addressDto.getHouse());
        entity.setApartment(addressDto.getApartment());
        entity.setSquare(addressDto.getSquare());
        return entity;
    }
}
