package com.tzuchaedahy.compass_ecommerce_challenge.infrastructure.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product.Product;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query(value = "select p.name, p.description, p.price, count(*) from product p where p.status = 0 GROUP BY p.name, p.description, p.price", nativeQuery = true)
    List<Object[]> findAllAvailable();
}
