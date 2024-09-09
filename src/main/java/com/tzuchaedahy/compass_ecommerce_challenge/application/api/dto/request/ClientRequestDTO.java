package com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public class ClientRequestDTO {
    @Schema(name = "cpf", example = "45445025004")
    private String cpf;
    @Schema(name = "name", example = "nome")
    private String name;
    @Schema(name = "email", example = "nome@teste.com")
    private String email;
    @Schema(name = "password", example = "Nome@123")
    private String password;

    public ClientRequestDTO(String cpf, String name, String email, String password) {
        this.cpf = cpf;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public ClientRequestDTO() {
    }

    public String getCpf() {
        return cpf;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
