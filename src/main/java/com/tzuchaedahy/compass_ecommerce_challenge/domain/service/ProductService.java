package com.tzuchaedahy.compass_ecommerce_challenge.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product.Product;
import com.tzuchaedahy.compass_ecommerce_challenge.infrastructure.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public void createNewProduct(Product product, Integer quantity) {
        for (int i = 0; i < quantity; i++) {
            product.setID(null);
            productRepository.save(product);
        }
    }
}
