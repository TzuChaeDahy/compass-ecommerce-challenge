package com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.response;

public class ProductResponseDTO {
    private String name;
    private String description;
    private Float price;
    private Integer quantity;

    public ProductResponseDTO(String name, String description, Float price, Integer quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
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

    public Integer getQuantity() {
        return quantity;
    }
}
