package com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.response;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.client.Client;

public class ClientResponseDTO {
    private String cpf;
    private String name;

    public ClientResponseDTO(Client client) {
        this.cpf = client.getCPF();
        this.name = client.getName();
    }

    public String getCpf() {
        return cpf;
    }

    public String getName() {
        return name;
    }

}
