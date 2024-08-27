package com.tzuchaedahy.compass_ecommerce_challenge.domain.model.status;

public enum Status {
    AVAILABLE(1),
    SELLED(2),
    UNAVAILABLE(3);

    private final Integer value;

    Status(Integer value) {
        this.value = value;
    }

    public Integer getStatus() {
        return this.value;
    }
}
