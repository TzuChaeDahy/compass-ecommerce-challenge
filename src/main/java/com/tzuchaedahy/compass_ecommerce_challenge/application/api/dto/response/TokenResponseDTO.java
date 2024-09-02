package com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.response;

public class TokenResponseDTO {
    private String token;

    public TokenResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}