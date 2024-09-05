package com.tzuchaedahy.compass_ecommerce_challenge.application.api.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.BuyRequestDTO;
import com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.response.BuyResponseDTO;
import com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.response.ClientBuyResponseDTO;
import com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.response.ClientResponseDTO;
import com.tzuchaedahy.compass_ecommerce_challenge.application.api.handler.exceptions.UnableToBuyNoItemsException;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.exception.DefaultError;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.buy.Buy;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.client.Client;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product.Product;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product.ProductBuilder;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product_buy.ProductBuy;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.service.BuyService;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.service.ClientService;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.service.ProductBuyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/buy")
public class ProductBuyHandler {

    @Autowired
    private BuyService buyService;

    @Autowired
    private ProductBuyService productBuyService;

    @Autowired
    private ClientService clientService;

    @PostMapping
    @Tag(name = "Rotas de Cliente")
    @Operation(summary = "Buy some products", description = "Buy some products with the given products id and quantity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Products created successfully", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = BuyResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = DefaultError.class))),
    })
    @Transactional
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
    public ResponseEntity<BuyResponseDTO> buyProducts(@RequestBody BuyRequestDTO buyRequestDTO,
            UsernamePasswordAuthenticationToken token) {
        if (buyRequestDTO.getItems().isEmpty()
                || buyRequestDTO.getItems().stream().anyMatch(item -> item.getQuantity() <= 0)) {
            throw new UnableToBuyNoItemsException("client cannot buy 0 items");
        }

        List<Product> boughtProducts = buyRequestDTO.getItems().stream().map(
                item -> {
                    Product product = new ProductBuilder()
                            .withID(item.getId())
                            .withStock(item.getQuantity())
                            .build();

                    return product;
                }).toList();

        Buy buy = buyService.createBuy(token.getName());

        List<ProductBuy> productBuys = productBuyService.buyProducts(buy, boughtProducts);

        BuyResponseDTO buyResponseDTO = new BuyResponseDTO(buy, productBuys);

        return new ResponseEntity<>(buyResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    @Tag(name = "Rotas de Cliente")
    @Operation(summary = "Get all buys", description = "Get all buys of the client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Buys retrieved successfully", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = BuyResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = DefaultError.class))),
    })
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
    public ResponseEntity<List<BuyResponseDTO>> getBuys(UsernamePasswordAuthenticationToken token) {
        List<Buy> buys = buyService.getBuys(token.getName());

        List<BuyResponseDTO> buyResponseDTOs = new ArrayList<>();

        buys.forEach(buy -> {
            List<ProductBuy> productBuys = productBuyService.getProductBuysByBuy(buy);

            var buyResponseDTO = new BuyResponseDTO(buy, productBuys);
            buyResponseDTOs.add(buyResponseDTO);
        });

        return new ResponseEntity<>(buyResponseDTOs, HttpStatus.OK);
    }

    @GetMapping("/all")
    @Tag(name = "Rotas de Administrador")
    @Operation(summary = "Get all buys from all clients", description = "Get all the buys from all the clients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Buys retrieved successfully", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ClientBuyResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = DefaultError.class))),
    })
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<List<ClientBuyResponseDTO>> getAllClientsBuys() {
        List<Client> clients = clientService.getAll();

        List<ClientBuyResponseDTO> clientBuyResponseDTOs = new ArrayList<>();
        clients.forEach(client -> {
            List<Buy> buys = buyService.getBuys(client.getEmail());

            List<BuyResponseDTO> buyResponseDTOs = new ArrayList<>();

            buys.forEach(buy -> {
                List<ProductBuy> productBuys = productBuyService.getProductBuysByBuy(buy);

                var buyResponseDTO = new BuyResponseDTO(buy, productBuys);
                buyResponseDTOs.add(buyResponseDTO);
            });

            clientBuyResponseDTOs.add(new ClientBuyResponseDTO(new ClientResponseDTO(client), buyResponseDTOs));
        });

        return new ResponseEntity<>(clientBuyResponseDTOs, HttpStatus.OK);
    }
}
