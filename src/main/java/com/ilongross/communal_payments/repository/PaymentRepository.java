package com.ilongross.communal_payments.repository;


import com.ilongross.communal_payments.model.entity.AccountEntity;
import com.ilongross.communal_payments.model.entity.PaymentEntity;
import com.ilongross.communal_payments.model.entity.ServiceTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {

    List<PaymentEntity> findByAccountId(AccountEntity accountId);

}