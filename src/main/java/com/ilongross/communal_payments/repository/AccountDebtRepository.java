package com.ilongross.communal_payments.repository;

import com.ilongross.communal_payments.model.entity.AccountDebtEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDebtRepository extends JpaRepository<AccountDebtEntity, Integer> {
}
