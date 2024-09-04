package com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product.exception.UnableToCreateProductException;

public class ProductBuilder {
    private final Product product;
    private final Map<String, String> errors;

    public ProductBuilder() {
        this.product = new Product();
        this.errors = new HashMap<>();
    }

    public ProductBuilder withID(UUID id) {
        if (id == null) {
            errors.put("id", "product id is invalid");
        }

        this.product.setID(id);
        return this;
    }

    public ProductBuilder withName(String name) {
        if (name == null || name.isBlank()) {
            this.errors.put("name", "product name is invalid");
        }

        this.product.setName(name);
        return this;
    }

    public ProductBuilder withDescription(String description) {
        if (description == null || description.isBlank() || description.length() < 10) {
            if (description != null && description.length() < 10) {
                this.errors.put("description", "product description is too short: it needs 10 characters at least");
            } else {
                this.errors.put("description", "product description is invalid");
            }
        }

        this.product.setDescription(description);
        return this;
    }

    public ProductBuilder withPrice(Float price) {
        if (price <= 0) {
            this.errors.put("price", "product price cannot be less than zero");
        }

        this.product.setPrice(price);
        return this;
    }

    public ProductBuilder withStock(Integer stock) {
        if (stock == null || stock < 0) {
            this.errors.put("stock", "product stock is invalid");
        }

        this.product.setStock(stock);
        return this;
    }

    public Product build() {
        if (!this.errors.isEmpty()) {
            throw new UnableToCreateProductException(this.errors);
        }

        return product;
    }
}
