package com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ClientBuyResponseDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ClientResponseDTO client;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<BuyResponseDTO> buys;

    public ClientBuyResponseDTO(ClientResponseDTO client, List<BuyResponseDTO> buys) {
        this.client = client;
        this.buys = buys;
    }

    public ClientResponseDTO getClient() {
        return client;
    }

    public void setClient(ClientResponseDTO client) {
        this.client = client;
    }

    public List<BuyResponseDTO> getBuys() {
        return buys;
    }

    public void setBuys(List<BuyResponseDTO> buys) {
        this.buys = buys;
    }

}
