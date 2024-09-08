package com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

public class TokenResponseDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;

    public TokenResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}