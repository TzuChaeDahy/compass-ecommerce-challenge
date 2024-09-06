package com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.client.Client;

public class ClientResponseDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String cpf;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;

    public ClientResponseDTO(Client client) {
        this.cpf = client.getCPF();
        this.email = client.getEmail();
        this.name = client.getName();
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

}
