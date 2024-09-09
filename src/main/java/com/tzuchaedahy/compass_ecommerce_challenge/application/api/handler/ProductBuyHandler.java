package com.tzuchaedahy.compass_ecommerce_challenge.application.api.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.request.BuyRequestDTO;
import com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.response.BuyResponseDTO;
import com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.response.ClientBuyResponseDTO;
import com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.response.ClientResponseDTO;
import com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.response.ReportResponseDTO;
import com.tzuchaedahy.compass_ecommerce_challenge.application.api.handler.exceptions.NoItemsBoughtException;
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
import com.tzuchaedahy.compass_ecommerce_challenge.domain.service.ProductService;

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

    @Autowired
    private ProductService productService;

    @PostMapping
    @Tag(name = "Rotas de Cliente")
    @Operation(summary = "Buy some products", description = "Buy some products of the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products bought successfully", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = BuyResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = DefaultError.class))),
    })
    @Transactional
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CONSUMER')")
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

        productBuyService.buyProducts(buy, boughtProducts);

        List<ProductBuy> productBuys = productBuyService.getProductBuysByBuy(buy);

        BuyResponseDTO buyResponseDTO = new BuyResponseDTO(buy, productBuys);

        return new ResponseEntity<>(buyResponseDTO, HttpStatus.OK);
    }

    @GetMapping
    @Tag(name = "Rotas de Cliente")
    @Operation(summary = "Get all buys", description = "Get all buys of the client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Buys retrieved successfully", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = BuyResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = DefaultError.class))),
    })
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CONSUMER')")
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

    @GetMapping("/report/{year}/{month}")
    @Tag(name = "Rotas de Administrador")
    @Operation(summary = "Get a report", description = "Get the report of sells of a given year and month")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Report retrieved successfully", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ReportResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = DefaultError.class))),
    })
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ReportResponseDTO> getReport(@PathVariable Integer year, @PathVariable Integer month) {
        Float[] totalRevenue = { 0f };
        Product mostSoldProduct = new Product();
        Client[] mostBuyerClient = { new Client() };

        List<Client> clients = clientService.getAll();

        Float[] spentByClient = { 0f };
        Map<UUID, Integer> productsSold = new HashMap<>();
        clients.forEach(client -> {
            client.setName(null);
            Float[] totalSpent = { 0f };

            List<Buy> buys = buyService.getBuys(client.getEmail());

            buys = buys.stream()
                    .filter(buy -> buy.getTimestamp().getYear() == year && buy.getTimestamp().getMonthValue() == month)
                    .toList();

            buys.forEach(buy -> {
                List<ProductBuy> productBuys = productBuyService.getProductBuysByBuy(buy);

                productBuys.forEach(productBuy -> {
                    totalSpent[0] += productBuy.getPriceAtMoment() * productBuy.getQuantity();
                    productsSold.merge(productBuy.getProduct().getID(), productBuy.getQuantity(), Integer::sum);
                });
            });

            totalRevenue[0] += totalSpent[0];
            if (totalSpent[0] > spentByClient[0]) {
                spentByClient[0] = totalSpent[0];
                mostBuyerClient[0] = client;
            }
        });

        if (totalRevenue[0] == 0) {
            throw new NoItemsBoughtException("no items bought in this period");
        }

        int maxQuantity = 0;
        UUID maxProductID = null;

        for (Map.Entry<UUID, Integer> entry : productsSold.entrySet()) {
            if (entry.getValue() > maxQuantity) {
                maxQuantity = entry.getValue();
                maxProductID = entry.getKey();
            }
        }

        mostSoldProduct = productService.findByID(maxProductID);
        mostSoldProduct.setPrice(null);
        mostSoldProduct.setStock(maxQuantity);

        ReportResponseDTO report = new ReportResponseDTO(totalRevenue[0], mostSoldProduct, mostBuyerClient[0]);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }
}
