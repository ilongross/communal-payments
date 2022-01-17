package com.ilongross.communal_payments.repository;


import com.ilongross.communal_payments.model.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {



}
