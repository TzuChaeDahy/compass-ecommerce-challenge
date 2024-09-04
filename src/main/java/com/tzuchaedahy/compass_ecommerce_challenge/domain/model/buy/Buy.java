package com.tzuchaedahy.compass_ecommerce_challenge.domain.model.buy;

import java.time.LocalDateTime;
import java.util.UUID;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.client.Client;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "buy")
public class Buy {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDateTime timestamp;

    @ManyToOne
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
