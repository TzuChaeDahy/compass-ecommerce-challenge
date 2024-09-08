package com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.client.Client;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product.Product;

public class ReportResponseDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Float totalRevenue;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ProductResponseDTO mostSoldProduct;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ClientResponseDTO mostBuyerClient;

    public ReportResponseDTO() {
    }

    public ReportResponseDTO(Float totalRevenue, Product mostSoldProduct,
            Client mostBuyerClient) {
        this.totalRevenue = totalRevenue;
        this.mostSoldProduct = new ProductResponseDTO(mostSoldProduct);
        this.mostBuyerClient = new ClientResponseDTO(mostBuyerClient);
    }

    public void setTotalRevenue(Float totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public void setMostSoldProduct(ProductResponseDTO mostSoldProduct) {
        this.mostSoldProduct = mostSoldProduct;
    }

    public void setMostBuyerClient(ClientResponseDTO mostBuyerClient) {
        this.mostBuyerClient = mostBuyerClient;
    }

    public Float getTotalRevenue() {
        return totalRevenue;
    }

    public ProductResponseDTO getMostSoldProduct() {
        return mostSoldProduct;
    }

    public ClientResponseDTO getMostBuyerClient() {
        return mostBuyerClient;
    }
}
