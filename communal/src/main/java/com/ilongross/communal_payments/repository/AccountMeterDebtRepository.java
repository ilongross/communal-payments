package com.ilongross.communal_payments.repository;

import com.ilongross.communal_payments.model.entity.AccountMeterDebtEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountMeterDebtRepository extends JpaRepository<AccountMeterDebtEntity, Integer> {
}
