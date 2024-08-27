package com.tzuchaedahy.compass_ecommerce_challenge.domain.model.buy;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.buy.exception.UnableToCreateBuyException;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.client.Client;

public class BuyBuilder {
    private final Buy buy;
    private final Map<String, String> errors;

    public BuyBuilder() {
        this.buy = new Buy();
        this.errors = new HashMap<>();
    }

    public BuyBuilder withID(UUID id) {
        if (id == null) {
            errors.put("id", "buy id cannot is invalid");
        }

        buy.setID(id);
        return this;
    }

    public BuyBuilder withTimestamp(LocalDateTime timestamp) {
        if (timestamp == null || timestamp.isAfter(LocalDateTime.now())) {
            errors.put("timestamp", "buy timestamp is invalid");
        }

        buy.setTimestamp(timestamp);
        return this;
    }

    public BuyBuilder withClient(Client client) {
        if (client == null) {
            errors.put("client", "buy client is invalid");
        }

        buy.setClient(client);
        return this;
    }

    public Buy build() {
        if (!errors.isEmpty()) {
            throw new UnableToCreateBuyException(errors);
        }

        return buy;
    }
}
