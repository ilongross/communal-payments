package com.ilongross.communal_payments.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "account_meter_debt", schema = "communal")
public class AccountMeterDebtEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "house_maintenance")
    private BigDecimal houseMaintenance;

    @Column(name = "current_maintenance")
    private BigDecimal currentMaintenance;

    @Column(name = "elevator")
    private BigDecimal elevatorMaintenance;

    @Column(name = "garbage_remove")
    private BigDecimal garbageRemove;

    @Column(name = "electricity")
    private BigDecimal electricity;

    @Column(name = "cold_water")
    private BigDecimal coldWater;

    @Column(name = "hot_water")
    private BigDecimal hotWater;

}
