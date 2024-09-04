package com.tzuchaedahy.compass_ecommerce_challenge.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product_buy.ProductBuy;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product_buy.ProductBuyKey;

public interface ProductBuyRepository extends JpaRepository<ProductBuy, ProductBuyKey> {

}
