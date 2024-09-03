package com.tzuchaedahy.compass_ecommerce_challenge.domain.service.exceptions;

import java.util.Map;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.exception.DefaultException;

public class UnableToAuthenticateClientException extends DefaultException {
    public UnableToAuthenticateClientException(String message) {
        super(Map.of("authentication", message));
    }
}
