package com.ilongross.communal_payments.test;


import com.ilongross.communal_payments.CommunalPaymentsApplication;
import com.ilongross.communal_payments.model.dto.*;
import com.ilongross.communal_payments.model.entity.AddressEntity;
import com.ilongross.communal_payments.model.mapper.AccountMapper;
import com.ilongross.communal_payments.model.mapper.AccountMapperCustom;
import com.ilongross.communal_payments.model.mapper.ServiceTypeMapper;
import com.ilongross.communal_payments.repository.AccountRepository;
import com.ilongross.communal_payments.repository.AddressRepository;
import com.ilongross.communal_payments.repository.ServiceTypeRepository;
import com.ilongross.communal_payments.service.PaymentService;
import com.ilongross.communal_payments.service.ServiceTypeService;
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
    private ServiceTypeService serviceTypeService;
    @Autowired
    private PaymentService paymentService;

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
                .accountId(account.getId())
                .serviceId(1)
                .value(new BigDecimal("100.2")).build();
        log.info("Address: {}", address);
        log.info("Meter: {}", meter);
    }

    @Test
    void insertEntities() {
        var addressEntity = new AddressEntity();
        addressEntity.setRegion("Иркутская область");
        addressEntity.setCity("Иркутск");
        addressEntity.setStreet("Университетский");
        addressEntity.setHouse("10");
        addressEntity.setApartment("10");
        addressEntity.setSquare(new BigDecimal("46.3"));
        addressRepository.save(addressEntity);

//        var accountEntity = new AccountEntity();
//        accountEntity.setId(2);
//        accountEntity.setName("Yakovleva");
//        accountEntity.setLastname("Ekaterina");
//        accountEntity.setPatronymic("Aleksandrovna");
//        accountEntity.setAddress(addressRepository.getById(4));
//        accountEntity.setEmail("kisskat@yandex.ru");
//        accountRepository.save(accountEntity);
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
        var paymentDto = PaymentDto.builder()
                .id(1)
                .accountId(1)
                .serviceId(3)
                .sum(new BigDecimal("1000.02")).build();
        var result = paymentService.makePayment(paymentDto);
        log.info("RESULT DTO: {}", result);
    }



}
