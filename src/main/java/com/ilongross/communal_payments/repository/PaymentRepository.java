package com.ilongross.communal_payments.repository;


import com.ilongross.communal_payments.model.dto.PaymentDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentDto, Integer> {
}
