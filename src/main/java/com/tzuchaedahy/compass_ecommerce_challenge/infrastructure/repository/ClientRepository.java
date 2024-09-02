package com.tzuchaedahy.compass_ecommerce_challenge.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.client.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    Optional<Client> findByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean existsByCpf(String cpf);
}
