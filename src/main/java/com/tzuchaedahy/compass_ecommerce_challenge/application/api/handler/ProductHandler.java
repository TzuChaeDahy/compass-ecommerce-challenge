package com.tzuchaedahy.compass_ecommerce_challenge.application.api.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.request.NewProductRequestDTO;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.exception.DefaultError;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product.Product;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product.ProductBuilder;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.status.Status;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/product")
public class ProductHandler {

    @Autowired
    private ProductService productService;
    
    @PostMapping("/new")
    @Tag(name = "Rotas de Administrador")
    @Operation(
        summary = "Create a new product",
        description = "Create a new product with the given name, description, price and quantity"
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
    public ResponseEntity<Void> createNewProducts(@RequestBody NewProductRequestDTO newProductRequestDTO) {
        Product product = new ProductBuilder()
            .withName(newProductRequestDTO.getName())
            .withDescription(newProductRequestDTO.getDescription())
            .withPrice(newProductRequestDTO.getPrice())
            .withStatus(Status.AVAILABLE)
            .build();

        productService.createNewProduct(product, newProductRequestDTO.getQuantity());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
