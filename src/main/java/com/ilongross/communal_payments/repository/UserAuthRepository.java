package com.ilongross.communal_payments.repository;

import com.ilongross.communal_payments.model.entity.UserAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthRepository extends JpaRepository<UserAuthEntity, Integer> {

    Optional<UserAuthEntity> findByUsername(String s);

}
