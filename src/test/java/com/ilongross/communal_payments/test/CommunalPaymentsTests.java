package com.ilongross.communal_payments.test;


import com.ilongross.communal_payments.CommunalPaymentsApplication;
import com.ilongross.communal_payments.model.dto.AccountDto;
import com.ilongross.communal_payments.model.dto.AddressDto;
import com.ilongross.communal_payments.model.dto.MeterDto;
import com.ilongross.communal_payments.model.dto.ServiceTypeDto;
import com.ilongross.communal_payments.model.entity.AccountEntity;
import com.ilongross.communal_payments.model.entity.AddressEntity;
import com.ilongross.communal_payments.model.mapper.AccountMapper;
import com.ilongross.communal_payments.model.mapper.AccountMapperCustom;
import com.ilongross.communal_payments.model.mapper.ServiceTypeMapper;
import com.ilongross.communal_payments.repository.AccountRepository;
import com.ilongross.communal_payments.repository.AddressRepository;
import com.ilongross.communal_payments.repository.ServiceTypeRepository;
import com.ilongross.communal_payments.service.ServiceTypeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@SpringBootTest(classes = CommunalPaymentsApplication.class)
@Slf4j
public class CommunalPaymentsTests {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AccountMapperCustom accountMapperCustom;
    @Autowired
    private ServiceTypeRepository serviceTypeRepository;
    @Autowired
    private ServiceTypeMapper serviceTypeMapper;
    @Autowired
    private ServiceTypeServiceImpl serviceTypeService;

    @Test
    void contextLoads() {
    }

    @Test
    void showDtos() {
        var address = AddressDto.builder()
                .id(1)
                .region("Irkutskaya")
                .city("Irkutsk")
                .street("Universitetsky")
                .house("10")
                .apartment("10").build();
        var account = AccountDto.builder()
                .id(1)
                .name("Ilya")
                .lastname("Zverev")
                .patronymic("Vladimirovich")
                .address(address.getId())
                .email("ilongrosss@gmail.com")
                .build();
        var meter = MeterDto.builder()
                .id(1)
                .accountID(account.getId())
                .service(1)
                .value(new BigDecimal("100.2")).build();
        log.info("Address: {}", address);
        log.info("Meter: {}", meter);
    }

    @Test
    void insertEntities() {
//        var addressEntity = new AddressEntity();
//        addressEntity.setId(1);
//        addressEntity.setRegion("Irkutskaya");
//        addressEntity.setCity("Irkutsk");
//        addressEntity.setStreet("Universitetsky");
//        addressEntity.setHouse("10");
//        addressEntity.setSquare(new BigDecimal("46.3"));
//        addressRepository.save(addressEntity);

        var accountEntity = new AccountEntity();
        accountEntity.setId(2);
        accountEntity.setName("Yakovleva");
        accountEntity.setLastname("Ekaterina");
        accountEntity.setPatronymic("Aleksandrovna");
        accountEntity.setAddress(addressRepository.getById(4));
        accountEntity.setEmail("kisskat@yandex.ru");
        accountRepository.save(accountEntity);
    }


    @Test
    void mappers() {
        System.out.println("------------------------------\n--- Account ---");
        var dtos = accountRepository.findAll().stream()
                .map(accountMapperCustom::mapToDto)
                .collect(Collectors.toList());
        System.out.println("Dtos:");
        dtos.forEach(System.out::println);

        System.out.println("Entities:");
        var entities = dtos.stream()
                .map(accountMapperCustom::mapToEntity).collect(Collectors.toList());
        entities.forEach(System.out::println);

        System.out.println("------------------------------\n--- ServiceType ---");
        var serviceTypeDtos = serviceTypeRepository.findAll().stream()
                .map(serviceTypeMapper::mapToDto)
                .collect(Collectors.toList());
        System.out.println("ServiceType DTOs:");
        serviceTypeDtos.forEach(System.out::println);
    }

    @Test
    void saveEntity() {
//        var newServiceType = ServiceTypeDto.builder()
//                .serviceName();
    }

    @Test
    void testPaymentMapping() {
//        var paymentDto =
    }



}
