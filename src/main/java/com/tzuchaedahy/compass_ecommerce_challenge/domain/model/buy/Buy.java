package com.tzuchaedahy.compass_ecommerce_challenge.domain.model.buy;

import java.time.LocalDateTime;
import java.util.UUID;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.client.Client;

public class Buy {
    private UUID id;
    private LocalDateTime timestamp;
    
    private Client client;

    public Buy() {
    }

    public UUID getID() {
        return id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Client getClient() {
        return client;
    }

    public void setID(UUID id) {
        this.id = id;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
