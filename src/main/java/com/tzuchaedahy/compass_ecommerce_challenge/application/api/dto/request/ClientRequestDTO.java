package com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.request;

public class ClientRequestDTO {
    private String cpf;
    private String name;
    private String email;
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
