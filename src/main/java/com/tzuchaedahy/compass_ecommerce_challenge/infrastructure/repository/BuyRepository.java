package com.tzuchaedahy.compass_ecommerce_challenge.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.buy.Buy;

public interface BuyRepository extends JpaRepository<Buy, UUID> {
    
}
