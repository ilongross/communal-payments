package com.ilongross.communal_payments.model.entity;

import com.ilongross.communal_payments.model.entity.id_classes.AccountDebtEntityId;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "account_debt", schema = "communal")
public class AccountDebtEntity {

    //TODO сделать реализацию таблицы с колонками по всем услугам

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "debt", nullable = false)
    private BigDecimal debt;

}