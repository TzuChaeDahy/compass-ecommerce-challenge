package com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.response;

import java.util.UUID;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product.Product;

public class BoughtProductResponseDTO {
    private UUID id;
    private String name;
    private Integer quantity;
    private Float price;

    public BoughtProductResponseDTO(Product product) {
        this.id = product.getID();
        this.name = product.getName();
        this.quantity = product.getStock();
        this.price = product.getPrice();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Float getPrice() {
        return price;
    }

    
}
