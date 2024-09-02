package com.tzuchaedahy.compass_ecommerce_challenge.domain.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.client.Client;

@Service
public class TokenService {

    @Value("${api.secret}")
    private String secret;

    public String generate(Client client) {
        // TODO: VERIFICAR NECESSIDADE DE USAR UM TRY CATCH
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withIssuer("jwt-auth")
                .withClaim("roles", client.getRoles().stream().map(setRole -> setRole.toString()).toList())
                .withExpiresAt(expiresAt())
                .withSubject(client.getEmail())
                .sign(algorithm);
    }

    public String validate(String token) {
        // TODO: VERIFICAR NECESSIDADE DE USAR UM TRY CATCH
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.require(algorithm)
                .withIssuer("jwt-auth")
                .build()
                .verify(token)
                .getSubject();
    }

    private Instant expiresAt() {
        return Instant.now().plusSeconds(3600);
    }
}
