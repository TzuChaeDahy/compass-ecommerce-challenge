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
import com.tzuchaedahy.compass_ecommerce_challenge.domain.exception.DefaultError;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.client.Client;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.client.ClientBuilder;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.role.Role;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.service.ClientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/client")
public class ClientHandler {
    @Autowired
    private ClientService clientService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @Tag(name = "Rotas de Autenticação")
    @Operation(summary = "Register a new client", description = "Register a new client in the system", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Client data", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ClientRequestDTO.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client registered successfully", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = TokenResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid data", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = DefaultError.class))),
    })
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
    @Tag(name = "Rotas de Autenticação")
    @Operation(summary = "Authenticate a client", description = "Authenticate a client in the system", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Client data", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = LoginRequestDTO.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged successfully", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = TokenResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid data", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = DefaultError.class))),
    })
    public ResponseEntity<TokenResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        String token = clientService.login(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());

        return new ResponseEntity<>(new TokenResponseDTO(token), HttpStatus.OK);
    }

    @PostMapping("/register/admin")
    @Tag(name = "Rotas de Administrador")
    @Operation(summary = "Register a new admin", description = "Register a new admin in the system", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Client data", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ClientRequestDTO.class))))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Admin registered successfully", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = TokenResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = DefaultError.class))),
    })
    @Transactional
    public ResponseEntity<TokenResponseDTO> registerAdmin(@RequestBody ClientRequestDTO clientRequestDTO) {
        Client client = new ClientBuilder()

                .withCPF(clientRequestDTO.getCpf())
                .withName(clientRequestDTO.getName())
                .withEmail(clientRequestDTO.getEmail())
                .withPasswordToEncode(clientRequestDTO.getPassword(), passwordEncoder)
                .withRoles(Set.of(Role.ADMIN, Role.CONSUMER))
                .build();

        String token = clientService.register(client);

        return new ResponseEntity<>(new TokenResponseDTO(token), HttpStatus.CREATED);
    }
}
