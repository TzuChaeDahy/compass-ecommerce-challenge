package com.tzuchaedahy.compass_ecommerce_challenge.application.api.handler.exceptions;

import java.util.Map;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.exception.DefaultException;

public class NoItemsBoughtException extends DefaultException {
    public NoItemsBoughtException(String message) {
        super(Map.of("items", message));
    }
}
