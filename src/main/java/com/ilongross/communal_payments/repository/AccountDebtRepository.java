package com.ilongross.communal_payments.repository;

import com.ilongross.communal_payments.model.entity.AccountDebtEntity;
import com.ilongross.communal_payments.model.entity.id_classes.AccountDebtEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDebtRepository extends JpaRepository<AccountDebtEntity, Integer> {
}
