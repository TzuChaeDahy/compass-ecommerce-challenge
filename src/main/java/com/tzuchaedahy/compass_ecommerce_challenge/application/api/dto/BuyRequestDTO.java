package com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto;

import java.util.List;

import com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.request.BuyItemRequestDTO;

public class BuyRequestDTO {
    private List<BuyItemRequestDTO> items;

    public BuyRequestDTO() {
    }

    public BuyRequestDTO(List<BuyItemRequestDTO> items) {
        this.items = items;
    }

    public List<BuyItemRequestDTO> getItems() {
        return items;
    }
}
