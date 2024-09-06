package com.tzuchaedahy.compass_ecommerce_challenge.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.client.Client;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.service.exceptions.UnableToAuthenticateClientException;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.service.exceptions.UserAlreadyRegisteredException;
import com.tzuchaedahy.compass_ecommerce_challenge.infrastructure.repository.ClientRepository;

import jakarta.transaction.Transactional;

@Service
public class ClientService implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return client;
    }

    public String register(Client client) {
        if (clientRepository.existsByEmail(client.getEmail()) || clientRepository.existsByCpf(client.getCPF())) {
            throw new UserAlreadyRegisteredException("user already registered");
        }

        Client registeredClient = clientRepository.save(client);
        
        return tokenService.generate(registeredClient);
    }

    @Transactional
    public String login(String email, String password) {
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new UnableToAuthenticateClientException("could not authenticate user"));

        Boolean match = passwordEncoder.matches(password, client.getPassword());

        if (!match) {
            throw new UnableToAuthenticateClientException("could not authenticate user");
        }

        return tokenService.generate(client);
    }

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    public Client updateClient(String name, Client client) {
        client.setName(name);

        return clientRepository.save(client);
    }
}
