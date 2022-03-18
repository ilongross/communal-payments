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

}
