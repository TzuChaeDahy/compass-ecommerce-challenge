package com.tzuchaedahy.compass_ecommerce_challenge.domain.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.client.Client;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.service.exceptions.UnableToAuthenticateClientException;

@Service
public class TokenService {

    @Value("${api.secret}")
    private String secret;

    public String generate(Client client) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("jwt-auth")
                    .withClaim("roles", client.getRoles().stream().map(setRole -> setRole.toString()).toList())
                    .withExpiresAt(expiresAt())
                    .withSubject(client.getEmail())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new UnableToAuthenticateClientException("token is invalid");
        }

    }

    public String validate(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("jwt-auth")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTDecodeException exception) {
            throw new UnableToAuthenticateClientException("token is invalid");
        }
    }

    private Instant expiresAt() {
        return Instant.now().plusSeconds(3600);
    }
}
