package com.tzuchaedahy.compass_ecommerce_challenge.domain.service.exceptions;

import java.util.Map;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.exception.DefaultException;

public class UserAlreadyRegisteredException extends DefaultException {
    public UserAlreadyRegisteredException(String message) {
        super(Map.of("service", message));
    }
}
