package com.ilongross.communal_payments.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "payment", schema = "communal")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private AccountEntity accountId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_id", referencedColumnName = "id", nullable = false)
    private ServiceTypeEntity serviceId;

    @Column(name = "sum", nullable = false)
    private BigDecimal sum;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

}