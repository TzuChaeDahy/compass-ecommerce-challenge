package com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product_buy;

import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Embeddable;

@Embeddable
public class ProductBuyKey {
    private UUID productID;
    private UUID buyID;

    public ProductBuyKey() {
    }

    public ProductBuyKey(UUID productID, UUID buyID) {
        this.productID = productID;
        this.buyID = buyID;
    }

    public UUID getProductID() {
        return productID;
    }

    public UUID getBuyID() {
        return buyID;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ProductBuyKey that = (ProductBuyKey) obj;
        return Objects.equals(productID, that.productID) && Objects.equals(buyID, that.buyID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productID, buyID);
    }

    
}
