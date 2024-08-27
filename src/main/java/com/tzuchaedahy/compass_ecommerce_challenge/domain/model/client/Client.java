package com.tzuchaedahy.compass_ecommerce_challenge.domain.model.client;

import java.util.List;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.role.Role;

public class Client {
    private String name;
    private String cpf;
    private String email;
    private String password;
    private List<Role> roles;

    public Client() {
    }

    public String getName() {
        return name;
    }

    public String getCPF() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCPF(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
