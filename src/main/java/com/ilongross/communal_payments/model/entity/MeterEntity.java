package com.ilongross.communal_payments.model.entity;


import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "meter", schema = "communal")
public class MeterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account", referencedColumnName = "id", nullable = false)
    private AccountEntity accountId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_type", referencedColumnName = "id", nullable = false)
    private ServiceTypeEntity serviceId;

    @Column(name = "value", nullable = false)
    private BigDecimal value;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

}
