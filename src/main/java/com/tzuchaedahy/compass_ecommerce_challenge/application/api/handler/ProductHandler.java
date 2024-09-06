package com.tzuchaedahy.compass_ecommerce_challenge.application.api.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.request.ProductRequestDTO;
import com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.response.ProductResponseDTO;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.exception.DefaultError;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product.Product;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product.ProductBuilder;
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
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ProductResponseDTO> createNewProducts(@RequestBody ProductRequestDTO newProductRequestDTO) {
        Product product = new ProductBuilder()
            .withName(newProductRequestDTO.getName())
            .withDescription(newProductRequestDTO.getDescription())
            .withPrice(newProductRequestDTO.getPrice())
            .withStock(newProductRequestDTO.getStock())
            .build();

        ProductResponseDTO createdProductDTO = new ProductResponseDTO(productService.createNewProduct(product));

        return new ResponseEntity<>(createdProductDTO, HttpStatus.CREATED);
    } 

    @GetMapping("/all")
    @Tag(name = "Rotas de Cliente (Não Autenticado)")
    @Operation(
        summary = "View all available products",
        description = "View all available products in the store"
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Products listed successfully",
            content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ProductResponseDTO.class))),
        }
    )
    public ResponseEntity<List<ProductResponseDTO>> listAllAvailableProducts() {
        List<Product> products = productService.listAll();

        List<ProductResponseDTO> productsDTO = products.stream().map(product -> new ProductResponseDTO(product)).toList();

        return new ResponseEntity<>(productsDTO, HttpStatus.OK);
    }
}
