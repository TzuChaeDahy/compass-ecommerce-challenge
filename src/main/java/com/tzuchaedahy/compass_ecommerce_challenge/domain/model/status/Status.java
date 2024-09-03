package com.tzuchaedahy.compass_ecommerce_challenge.domain.model.status;

public enum Status {
    AVAILABLE(0),
    SELLED(1),
    UNAVAILABLE(2);

    private final Integer value;

    Status(Integer value) {
        this.value = value;
    }

    public Integer getStatus() {
        return this.value;
    }
}
