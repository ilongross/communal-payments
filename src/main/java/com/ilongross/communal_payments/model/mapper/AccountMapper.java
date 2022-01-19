package com.ilongross.communal_payments.model.mapper;

import com.ilongross.communal_payments.model.dto.AccountDto;
import com.ilongross.communal_payments.model.entity.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "address", source = "address.id")
    AccountDto mapToDto(AccountEntity entity);

//    AccountEntity mapToEntity(AccountDto dto);

}
