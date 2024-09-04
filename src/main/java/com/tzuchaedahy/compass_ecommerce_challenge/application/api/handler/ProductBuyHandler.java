package com.tzuchaedahy.compass_ecommerce_challenge.application.api.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.BuyRequestDTO;
import com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.response.BuyResponseDTO;
import com.tzuchaedahy.compass_ecommerce_challenge.application.api.handler.exceptions.UnableToBuyNoItemsException;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.exception.DefaultError;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.buy.Buy;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product.Product;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product.ProductBuilder;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product_buy.ProductBuy;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.service.BuyService;
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

    @PostMapping
    @Tag(name = "Rotas de Cliente")
    @Operation(
        summary = "Buy some products",
        description = "Buy some products with the given products id and quantity"
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201", description = "Products created successfully",
            content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", 
            content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = DefaultError.class))
            ),
        }
    )
    @Transactional
    public ResponseEntity<BuyResponseDTO> buyProducts(@RequestBody BuyRequestDTO buyRequestDTO, UsernamePasswordAuthenticationToken token) {
        if (buyRequestDTO.getItems().isEmpty() || buyRequestDTO.getItems().stream().anyMatch(item -> item.getQuantity() <= 0)) {
            throw new UnableToBuyNoItemsException("client cannot buy 0 items");
        }

        List<Product> boughtProducts = buyRequestDTO.getItems().stream().map(
            item -> {
                Product product = new ProductBuilder()
                    .withID(item.getId())
                    .withStock(item.getQuantity())
                    .build();

                return product;
            }
        ).toList();

        Buy buy = buyService.createBuy(token.getName());
        
        List<ProductBuy> productBuys = productBuyService.buyProducts(buy, boughtProducts);

        BuyResponseDTO buyResponseDTO = new BuyResponseDTO(buy, productBuys);

        return new ResponseEntity<>(buyResponseDTO, HttpStatus.CREATED);
    }
    
}
