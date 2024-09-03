package com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.request;

public class NewProductRequestDTO {
    private String name;
    private String description;
    private Float price;
    private Integer quantity;

    public NewProductRequestDTO(String name, String description, Float price, Integer quantity) {
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
