package com.ilongross.communal_payments.repository;

import com.ilongross.communal_payments.model.entity.MeterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeterRepository extends JpaRepository<MeterEntity, Integer> {
}
