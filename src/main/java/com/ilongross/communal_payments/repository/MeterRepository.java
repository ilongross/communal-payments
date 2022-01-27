package com.ilongross.communal_payments.repository;

import com.ilongross.communal_payments.model.dto.AccountDto;
import com.ilongross.communal_payments.model.entity.AccountEntity;
import com.ilongross.communal_payments.model.entity.MeterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeterRepository extends JpaRepository<MeterEntity, Integer> {

    List<MeterEntity> findByAccountId(AccountEntity accountID);

}
