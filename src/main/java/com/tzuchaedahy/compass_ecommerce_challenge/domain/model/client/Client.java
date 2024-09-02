package com.tzuchaedahy.compass_ecommerce_challenge.domain.model.client;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.buy.Buy;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.role.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "client")
public class Client implements UserDetails {
    @Id
    private String cpf;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.ORDINAL)
    @ElementCollection(targetClass = Role.class)
    @CollectionTable(name = "client_role", joinColumns = @JoinColumn(name = "cpf"))
    private Set<Role> roles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    private Set<Buy> buys;

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

    @Override
    public String getPassword() {
        return password;
    }

    public Set<Buy> getBuys() {
        return buys;
    }   

    public java.util.Set<Role> getRoles() {
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

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setBuys(Set<Buy> buys) {
        this.buys = buys;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.toString())).toList(); 
    }

    @Override
    public String getUsername() {
        return email;
    }
}
