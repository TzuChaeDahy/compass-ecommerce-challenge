package com.tzuchaedahy.compass_ecommerce_challenge.domain.model.role;

public enum Role {
    ADMIN(0),
    CONSUMER(1);

    private final Integer value;

    private Role(Integer value) {
        this.value = value;
    }

    public Integer getRole() {
        return this.value;
    }
}
