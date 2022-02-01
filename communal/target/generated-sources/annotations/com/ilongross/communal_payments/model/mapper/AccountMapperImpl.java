package com.ilongross.communal_payments.model.mapper;

import com.ilongross.communal_payments.model.dto.AccountDto;
import com.ilongross.communal_payments.model.dto.AccountDto.AccountDtoBuilder;
import com.ilongross.communal_payments.model.entity.AccountEntity;
import com.ilongross.communal_payments.model.entity.AddressEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-31T03:46:03+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 16.0.2 (Oracle Corporation)"
)
@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public AccountDto mapToDto(AccountEntity entity) {
        if ( entity == null ) {
            return null;
        }

        AccountDtoBuilder accountDto = AccountDto.builder();

        accountDto.address( entityAddressId( entity ) );
        accountDto.id( entity.getId() );
        accountDto.name( entity.getName() );
        accountDto.lastname( entity.getLastname() );
        accountDto.patronymic( entity.getPatronymic() );
        accountDto.email( entity.getEmail() );

        return accountDto.build();
    }

    private Integer entityAddressId(AccountEntity accountEntity) {
        if ( accountEntity == null ) {
            return null;
        }
        AddressEntity address = accountEntity.getAddress();
        if ( address == null ) {
            return null;
        }
        Integer id = address.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
