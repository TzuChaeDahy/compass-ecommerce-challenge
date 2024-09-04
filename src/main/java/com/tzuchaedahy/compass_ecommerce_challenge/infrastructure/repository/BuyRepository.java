package com.tzuchaedahy.compass_ecommerce_challenge.infrastructure.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.buy.Buy;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.client.Client;

public interface BuyRepository extends JpaRepository<Buy, UUID> {

    List<Buy> findAllByClient(Client client);
    
}
