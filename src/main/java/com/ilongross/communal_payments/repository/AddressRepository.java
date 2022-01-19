package com.ilongross.communal_payments.repository;

import com.ilongross.communal_payments.model.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity, Integer> {

}
