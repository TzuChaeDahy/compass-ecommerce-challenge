package com.tzuchaedahy.compass_ecommerce_challenge.infrastructure.config;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.client.Client;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.client.ClientBuilder;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.role.Role;
import com.tzuchaedahy.compass_ecommerce_challenge.infrastructure.repository.ClientRepository;

@Configuration
public class BeanConfig implements CommandLineRunner {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void run(String... args) throws Exception {
        Client client = new ClientBuilder()
                .withCPF("53684189065")
                .withName("Admin").withEmail("admin@teste.com")
                .withPasswordToEncode("Admin@123", passwordEncoder)
                .withRoles(Set.of(Role.ADMIN, Role.CONSUMER))
                .build();

        if (!clientRepository.findByEmail("admin@teste.com").isPresent()) {
            clientRepository.save(client);
        }
    }
}
