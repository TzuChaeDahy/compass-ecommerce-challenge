package com.tzuchaedahy.compass_ecommerce_challenge.domain.model.buy.exception;

import java.util.Map;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.exception.DefaultException;

public class UnableToCreateBuyException extends DefaultException {
    public UnableToCreateBuyException(Map<String, String> errors) {
        super(errors);
    }
}
