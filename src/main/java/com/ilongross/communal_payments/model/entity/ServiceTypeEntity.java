package com.ilongross.communal_payments.model.entity;

import liquibase.pro.packaged.I;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "service_type", schema = "communal")
public class ServiceTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "service_name", nullable = false)
    private String serviceName;

    @Column(name = "tariff", nullable = false)
    private BigDecimal tariff;

}
