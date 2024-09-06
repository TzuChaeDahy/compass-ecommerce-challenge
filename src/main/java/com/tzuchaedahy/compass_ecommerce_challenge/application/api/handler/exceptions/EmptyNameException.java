package com.tzuchaedahy.compass_ecommerce_challenge.application.api.handler.exceptions;

import java.util.Map;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.exception.DefaultException;

public class EmptyNameException extends DefaultException {
    public EmptyNameException(String message) {
        super(Map.of("name", message));
    }

}
