package com.tzuchaedahy.compass_ecommerce_challenge.domain.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.buy.Buy;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.buy.BuyBuilder;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.client.Client;
import com.tzuchaedahy.compass_ecommerce_challenge.infrastructure.repository.BuyRepository;
import com.tzuchaedahy.compass_ecommerce_challenge.infrastructure.repository.ClientRepository;

@Service
public class BuyService {

    @Autowired
    private BuyRepository buyRepository;

    @Autowired
    private ClientRepository clientRepository;

    public Buy createBuy(String email) {
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("client not found"));

        Buy buy = new BuyBuilder().withClient(client)
                .withTimestamp(LocalDateTime.now())
                .build();

        return buyRepository.save(buy);
    }
}
