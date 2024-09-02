package com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product.exception;

import java.util.Map;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.exception.DefaultException;

public class UnableToCreateProductException extends DefaultException {
    public UnableToCreateProductException(Map<String, String> errors) {
        super(errors);
    }
}
