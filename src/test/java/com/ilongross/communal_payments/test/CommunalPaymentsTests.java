package com.ilongross.communal_payments.test;


import com.ilongross.communal_payments.CommunalPaymentsApplication;
import com.ilongross.communal_payments.model.dto.AccountDto;
import com.ilongross.communal_payments.model.dto.AddressDto;
import com.ilongross.communal_payments.model.dto.MeterDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest(classes = CommunalPaymentsApplication.class)
@Slf4j
public class CommunalPaymentsTests {

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
                .square(new BigDecimal("46"))
                .address(address)
                .email("ilongrosss@gmail.com")
                .build();
        var meter = MeterDto.builder()
                .id(1)
                .userId(account.getId())
                .service("Водоотведение")
                .value(new BigDecimal("100.2")).build();
        log.info("Address: {}", address);
        log.info("Account: {}", account);
        log.info("Meter: {}", meter);
    }


}
