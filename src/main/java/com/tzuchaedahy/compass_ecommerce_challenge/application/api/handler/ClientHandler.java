package com.tzuchaedahy.compass_ecommerce_challenge.application.api.handler;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.request.ClientRequestDTO;
import com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.request.LoginRequestDTO;
import com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.response.TokenResponseDTO;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.client.Client;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.client.ClientBuilder;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.role.Role;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.service.ClientService;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;


@RestController
@RequestMapping("/client")
public class ClientHandler {
    @Autowired
    private ClientService clientService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<TokenResponseDTO> register(@RequestBody ClientRequestDTO clientRequestDTO) {
        Client client = new ClientBuilder()
                .withCPF(clientRequestDTO.getCpf())
                .withName(clientRequestDTO.getName())
                .withEmail(clientRequestDTO.getEmail())
                .withPasswordToEncode(clientRequestDTO.getPassword(), passwordEncoder)
                .withRoles(Set.of(Role.CONSUMER))
                .build();

        String token = clientService.register(client);

        return new ResponseEntity<>(new TokenResponseDTO(token), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @SecurityRequirements
    public ResponseEntity<TokenResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        String token = clientService.login(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());

        return new ResponseEntity<>(new TokenResponseDTO(token), HttpStatus.OK);
    }
}
