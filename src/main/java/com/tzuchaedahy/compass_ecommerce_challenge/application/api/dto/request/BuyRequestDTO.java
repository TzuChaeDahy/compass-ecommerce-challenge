package com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.request;

import java.util.List;

public class BuyRequestDTO {
    private List<BuyItemRequestDTO> items;

    public BuyRequestDTO() {
    }

    public List<BuyItemRequestDTO> getItems() {
        return items;
    }

    public void setItems(List<BuyItemRequestDTO> items) {
        this.items = items;
    }

}
