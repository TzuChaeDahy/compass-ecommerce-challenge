package com.tzuchaedahy.compass_ecommerce_challenge.infrastructure.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.client.Client;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.service.TokenService;
import com.tzuchaedahy.compass_ecommerce_challenge.infrastructure.repository.ClientRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ClientRepository clientRepository;

    private String getEmailFromAuthorizationHeader(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null) {
            return null;
        }

        token.replace("Bearer ", "");

        return tokenService.validate(token);
    }

    // TODO: Lembrar de remover isso
    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String email = getEmailFromAuthorizationHeader(request);

        if (email != null) {
            Client client = clientRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Client not found"));

            var authentication = new UsernamePasswordAuthenticationToken(client.getEmail(), null,
                    client.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        doFilter(request, response, filterChain);
    }

}
