package com.tzuchaedahy.compass_ecommerce_challenge.application.api.handler;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.request.ProductRequestDTO;
import com.tzuchaedahy.compass_ecommerce_challenge.application.api.dto.response.ProductResponseDTO;
import com.tzuchaedahy.compass_ecommerce_challenge.application.api.handler.exceptions.EmptyNameException;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.exception.DefaultError;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product.Product;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product.ProductBuilder;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;


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

    @PutMapping("/update/{uuid}")
    @Tag(name = "Rotas de Administrador")
    @Operation(summary = "Update a product", parameters = @Parameter(name = "uuid", description = "UUID of the product to update", in = ParameterIn.PATH, required = true), description = "Update the name, description, price and stock of a product", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Client data", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ProductRequestDTO.class))))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product updated successfully", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ProductResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = DefaultError.class))),
    })
    @Transactional
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable UUID uuid, @RequestBody ProductRequestDTO productRequestDTO) {
        if (productRequestDTO.getName() == null || productRequestDTO.getName().isEmpty()) {
            throw new EmptyNameException("name cannot be empty");
        }
        if (productRequestDTO.getDescription() == null || productRequestDTO.getDescription().isEmpty()) {
            throw new EmptyNameException("description cannot be empty");
        }
        if (productRequestDTO.getPrice() == null || productRequestDTO.getPrice() <= 0) {
            throw new EmptyNameException("price cannot less than one");
        }
        if (productRequestDTO.getStock() == null || productRequestDTO.getStock() < 0) {
            throw new EmptyNameException("stock cannot less than zero");
        }

        Product product = productService.findByID(uuid);

        Product customProduct = new ProductBuilder()
            .withName(productRequestDTO.getName())
            .withDescription(productRequestDTO.getDescription())
            .withPrice(productRequestDTO.getPrice())
            .withStock(productRequestDTO.getStock())
            .build();

        Product updatedProduct = productService.updateProduct(product, customProduct);

        ProductResponseDTO productDTO = new ProductResponseDTO(updatedProduct);
        
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }
}
