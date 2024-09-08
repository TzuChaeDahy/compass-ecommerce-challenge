package com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.response;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product.Product;

public class BoughtProductResponseDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer quantity;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Float price;

    public BoughtProductResponseDTO(Product product) {
        this.id = product.getID();
        this.name = product.getName();
        this.quantity = product.getStock();
        this.price = product.getPrice();
    }

    public BoughtProductResponseDTO(Product product, Integer quantity) {
        this.id = product.getID();
        this.name = product.getName();
        this.quantity = quantity;
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
