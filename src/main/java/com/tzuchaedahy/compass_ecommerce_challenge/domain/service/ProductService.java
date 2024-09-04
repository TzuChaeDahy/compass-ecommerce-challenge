package com.tzuchaedahy.compass_ecommerce_challenge.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product.Product;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.service.exceptions.ProductAlreadyRegisteredException;
import com.tzuchaedahy.compass_ecommerce_challenge.infrastructure.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product createNewProduct(Product product) {
        if (productRepository.existsByNameAndDescription(product.getName(), product.getDescription())) {
            throw new ProductAlreadyRegisteredException("product already registered");
        }
        
        return productRepository.save(product);
    }

    public List<Product> listAll() {
        return productRepository.findAll();
    }
}
