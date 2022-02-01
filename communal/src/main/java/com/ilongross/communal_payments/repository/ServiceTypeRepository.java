package com.ilongross.communal_payments.repository;

import com.ilongross.communal_payments.model.entity.ServiceTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceTypeRepository extends JpaRepository<ServiceTypeEntity, Integer> {
}
