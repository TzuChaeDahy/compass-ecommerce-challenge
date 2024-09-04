package com.tzuchaedahy.compass_ecommerce_challenge.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product.Product;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Boolean existsByNameAndDescription(String name, String description);
}
