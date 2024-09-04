package com.tzuchaedahy.compass_ecommerce_challenge.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.buy.Buy;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product.Product;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product_buy.ProductBuy;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product_buy.ProductBuyBuilder;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product_buy.ProductBuyKey;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.service.exceptions.ProductNotFoundException;
import com.tzuchaedahy.compass_ecommerce_challenge.infrastructure.repository.ProductBuyRepository;
import com.tzuchaedahy.compass_ecommerce_challenge.infrastructure.repository.ProductRepository;

@Service
public class ProductBuyService {
    @Autowired
    private ProductBuyRepository buyRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<ProductBuy> buyProducts(Buy buy, List<Product> incompleteBoughtProducts) {
        List<ProductBuy> productBuys = new ArrayList<>();
        List<Product> completeBoughtProducts = incompleteBoughtProducts.stream().map(
                incompleteProduct -> {
                    Product completeProduct = productRepository.findById(incompleteProduct.getID())
                            .orElseThrow(() -> new ProductNotFoundException("product not found"));

                    int newStock = completeProduct.getStock() - incompleteProduct.getStock();
                    completeProduct.setStock(newStock);

                    productRepository.save(completeProduct);

                    return completeProduct;
                }).toList();

        for (Product product : completeBoughtProducts) {
            ProductBuy productBuy = new ProductBuyBuilder()
                    .withId(new ProductBuyKey(product.getID(), buy.getID()))
                    .withBuy(buy)
                    .withProduct(product)
                    .withQuantity(incompleteBoughtProducts.stream()
                            .filter(p -> p.getID().equals(product.getID()))
                            .findFirst()
                            .get()
                            .getStock())
                    .withPriceAtMoment(product.getPrice())
                    .build();

            productBuys.add(buyRepository.save(productBuy));
        }

        for (Product incompleteProduct : incompleteBoughtProducts) {
            completeBoughtProducts.stream()
                    .filter(cp -> cp.getID().equals(incompleteProduct.getID())).findFirst().get()
                    .setStock(incompleteProduct.getStock());
        }

        return productBuys;
    }
}
