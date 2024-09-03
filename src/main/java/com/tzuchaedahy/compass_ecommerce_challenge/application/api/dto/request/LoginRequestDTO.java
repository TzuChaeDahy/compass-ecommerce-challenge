package com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public class LoginRequestDTO {
    @Schema(description = "email", example = "nome@teste.com")
    private String email;
    @Schema(description = "password", example = "Nome@123")
    private String password;

    public LoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
