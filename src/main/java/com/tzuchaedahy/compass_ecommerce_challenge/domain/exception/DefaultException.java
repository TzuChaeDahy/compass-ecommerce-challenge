package com.tzuchaedahy.compass_ecommerce_challenge.domain.exception;

import java.util.HashMap;
import java.util.Map;

public class DefaultException extends RuntimeException {
    private final Map<String, String> messages = new HashMap<>();

    public DefaultException() {
    }

    public DefaultException(Map<String, String> messages) {
        this.messages.putAll(messages);
    }

    public Map<String, String> getMessages() {
        return messages;
    }
}
