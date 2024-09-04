package com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.response;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product.Product;

public class ProductResponseDTO {
    private String name;
    private String description;
    private Float price;
    private Integer stock;

    public ProductResponseDTO(String name, String description, Float price, Integer stock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public ProductResponseDTO(Product product) {
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.stock = product.getStock();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Float getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }
}
