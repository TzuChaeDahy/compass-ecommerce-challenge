package com.tzuchaedahy.compass_ecommerce_challenge.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.client.Client;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.service.exceptions.UserAlreadyRegisteredException;
import com.tzuchaedahy.compass_ecommerce_challenge.infrastructure.repository.ClientRepository;

@Service
public class ClientService implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));    

        return client;
    }

    public Client register(Client client) {
        if (clientRepository.existsByEmail(client.getEmail()) || clientRepository.existsByCpf(client.getCPF())) {
            throw new UserAlreadyRegisteredException("user already registered");
        }

        return clientRepository.save(client);
    }
}
