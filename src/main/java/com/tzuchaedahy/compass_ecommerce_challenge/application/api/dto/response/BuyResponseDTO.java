package com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.response;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.buy.Buy;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product_buy.ProductBuy;

public class BuyResponseDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime timestamp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<BoughtProductResponseDTO> products;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Float total = 0f;

    public BuyResponseDTO(Buy buy, List<ProductBuy> productBuys) {
        this.id = buy.getID();
        this.timestamp = buy.getTimestamp();
        this.products = productBuys.stream().map(productBuy -> new BoughtProductResponseDTO(productBuy.getProduct(), productBuy.getQuantity()))
                .toList();
        this.products.forEach(product -> {
            total += product.getPrice() * product.getQuantity();
        });
    }

    public UUID getId() {
        return id;
    }

    public OffsetDateTime getTimestamp() {
        return OffsetDateTime.of(timestamp, ZoneOffset.UTC);
    }

    public List<BoughtProductResponseDTO> getProducts() {
        return products;
    }

    public Float getTotal() {
        return total;
    }

}
