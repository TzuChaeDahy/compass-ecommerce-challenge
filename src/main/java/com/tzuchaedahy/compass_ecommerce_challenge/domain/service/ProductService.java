package com.tzuchaedahy.compass_ecommerce_challenge.domain.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product.Product;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product.ProductBuilder;
import com.tzuchaedahy.compass_ecommerce_challenge.infrastructure.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public void createNewProduct(Product product, Integer quantity) {
        // TODO: QUANTITY CANNOT BE LESS THAN ONE
        for (int i = 0; i < quantity; i++) {
            product.setID(null);
            productRepository.save(product);
        }
    }

    public Map<Product, Integer> listAllAvailableProducts() {
        List<Object[]> results = productRepository.findAllAvailable();

        Map<Product, Integer> products = results.stream().map(
            product -> {
                Product newProduct = new ProductBuilder()
                .withName((String) product[0])
                .withDescription((String) product[1])
                .withPrice((Float) product[2])
                .build();

                return Map.entry(newProduct, ((Long) product[3]).intValue());
            }                
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return products;
    }
}
