package com.tzuchaedahy.compass_ecommerce_challenge.domain.service.exceptions;

import java.util.Map;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.exception.DefaultException;

public class ProductNotFoundException extends DefaultException {
    public ProductNotFoundException(String message) {
        super(Map.of("product", message));
    }
}
