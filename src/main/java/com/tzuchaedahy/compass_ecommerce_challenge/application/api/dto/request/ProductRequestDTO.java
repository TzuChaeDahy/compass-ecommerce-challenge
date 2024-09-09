package com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public class ProductRequestDTO {
    @Schema(description = "Name of the product", example = "Computador")
    private String name;
    @Schema(description = "Description of the product", example = "Descrição do Computador")
    private String description;
    @Schema(description = "Price of the product", example = "3500.00")
    private Float price;
    @Schema(description = "Stock of the product", example = "40")
    private Integer stock;

    public ProductRequestDTO(String name, String description, Float price, Integer stock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

}
