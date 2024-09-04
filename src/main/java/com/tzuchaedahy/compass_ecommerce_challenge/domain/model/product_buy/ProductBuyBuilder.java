package com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product_buy;

import java.util.HashMap;
import java.util.Map;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.buy.Buy;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product.Product;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product.exception.UnableToCreateProductException;

public class ProductBuyBuilder {
    private final ProductBuy productBuy;
    private final Map<String, String> errors;

    public ProductBuyBuilder() {
        this.productBuy = new ProductBuy();
        this.errors = new HashMap<>();
    }

    public ProductBuyBuilder withId(ProductBuyKey id) {
        if (id == null) {
            errors.put("id", "product buy id is invalid");
        }

        this.productBuy.setId(id);
        return this;
    }

    public ProductBuyBuilder withProduct(Product product) {
        if (product == null) {
            this.errors.put("product", "product is invalid");
        }

        this.productBuy.setProduct(product);
        return this;
    }

    public ProductBuyBuilder withBuy(Buy buy) {
        if (buy == null) {
            this.errors.put("buy", "buy is invalid");
        }

        this.productBuy.setBuy(buy);
        return this;
    }

    public ProductBuyBuilder withPriceAtMoment(Float priceAtMoment) {
        if (priceAtMoment == null || priceAtMoment <= 0) {
            this.errors.put("priceAtMoment", "price at moment is invalid");
        }

        this.productBuy.setPriceAtMoment(priceAtMoment);
        return this;
    }

    public ProductBuyBuilder withQuantity(Integer quantity) {
        if (quantity == null || quantity < 0) {
            this.errors.put("quantity", "quantity is invalid");
        }

        this.productBuy.setQuantity(quantity);
        return this;
    }

    public ProductBuy build() {
        if (!this.errors.isEmpty()) {
            throw new UnableToCreateProductException(this.errors);
        }

        return productBuy;
    }
}
