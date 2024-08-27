package com.tzuchaedahy.compass_ecommerce_challenge.domain.model.client.exception;

import java.util.Map;

import com.tzuchaedahy.compass_ecommerce_challenge.exception.DefaultException;

public class UnableToCreateClientException extends DefaultException {
    public UnableToCreateClientException(Map<String, String> errors) {
        super(errors);
    }
}
