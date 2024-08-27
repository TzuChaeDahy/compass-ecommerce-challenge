package com.tzuchaedahy.compass_ecommerce_challenge.domain.model.client;

import static com.tzuchaedahy.compass_ecommerce_challenge.util.Validations.isCPFValid;
import static com.tzuchaedahy.compass_ecommerce_challenge.util.Validations.isEmailValid;
import static com.tzuchaedahy.compass_ecommerce_challenge.util.Validations.isPasswordValid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.client.exception.UnableToCreateClientException;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.role.Role;

public class ClientBuilder {
    private final Client client;
    private final Map<String, String> errors;

    public ClientBuilder() {
        this.client = new Client();
        this.errors = new HashMap<>();
    }

    public ClientBuilder withName(String name) {
        if (name == null || name.isBlank()) {
            this.errors.put("name", "client name is invalid");
        }

        this.client.setName(name);
        return this;
    }

    public ClientBuilder withCPF(String cpf) {
        if (cpf == null || cpf.isBlank() || !isCPFValid(cpf)) {
            this.errors.put("cpf", "client cpf is invalid");
        }

        this.client.setCPF(cpf);
        return this;
    }

    public ClientBuilder withEmail(String email) {
        if (email == null || email.isBlank() || !isEmailValid(email)) {
            this.errors.put("email", "client email is invalid");
        }

        this.client.setEmail(email);
        return this;
    }

    public ClientBuilder withPassword(String password) {
        if (password == null || password.isBlank() || !isPasswordValid(password)) {
            this.errors.put("password", "client password is invalid");
        }

        this.client.setPassword(password);
        return this;
    }

    public ClientBuilder withRoles(List<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            this.errors.put("roles", "client roles are invalid");
        }

        this.client.setRoles(roles);
        return this;
    }

    public Client build() {
        if (!this.errors.isEmpty()) {
            throw new UnableToCreateClientException(this.errors);
        }

        return this.client;
    }
}
