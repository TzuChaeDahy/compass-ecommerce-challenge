package com.tzuchaedahy.compass_ecommerce_challenge.domain.exception;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

public class DefaultError {
    private LocalDateTime timestamp;
    private Map<String, String> errors;

    public DefaultError(Map<String, String> errors) {
        this.timestamp = LocalDateTime.now();
        this.errors = errors;
    }

    public DefaultError(String message) {
        this.timestamp = LocalDateTime.now();
        this.errors = new HashMap<>();
        this.errors.put("error", message);
    }

    public OffsetDateTime getTimestamp() {
        return OffsetDateTime.of(timestamp, ZoneOffset.UTC);
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
