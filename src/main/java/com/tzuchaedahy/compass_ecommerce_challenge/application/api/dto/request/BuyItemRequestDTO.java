package com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.request;

import java.util.UUID;

public class BuyItemRequestDTO {
    private UUID id;
    private Integer quantity;

    public BuyItemRequestDTO(UUID id, Integer quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public UUID getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
