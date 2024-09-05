package com.tzuchaedahy.compass_ecommerce_challenge.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.buy.Buy;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product.Product;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product.ProductBuilder;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product_buy.ProductBuy;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product_buy.ProductBuyBuilder;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product_buy.ProductBuyKey;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.service.exceptions.NotEnoughStockException;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.service.exceptions.ProductNotFoundException;
import com.tzuchaedahy.compass_ecommerce_challenge.infrastructure.repository.ProductBuyRepository;
import com.tzuchaedahy.compass_ecommerce_challenge.infrastructure.repository.ProductRepository;


@Service
public class ProductBuyService {
    @Autowired
    private ProductBuyRepository productBuyRepository;

    @Autowired
    private ProductRepository productRepository;

    private List<Product> mergeDuplicatedProducts(List<Product> incompleteBoughtProducts) {
        Map<UUID, Integer> productQuantities = new HashMap<>();

        for (Product product : incompleteBoughtProducts) {
            UUID productId = product.getID();
            int quantity = product.getStock();

            if (productQuantities.containsKey(productId)) {
                quantity += productQuantities.get(productId);
            }

            productQuantities.put(productId, quantity);
        }

        List<Product> mergedProducts = new ArrayList<>();
        for (Map.Entry<UUID, Integer> entry : productQuantities.entrySet()) {
            UUID productId = entry.getKey();
            int quantity = entry.getValue();

            Product mergedProduct = new ProductBuilder().withID(productId).withStock(quantity).build();
            mergedProducts.add(mergedProduct);
        }

        return mergedProducts;
    }

    public void buyProducts(Buy buy, List<Product> incompleteBoughtProducts) {
        incompleteBoughtProducts = mergeDuplicatedProducts(incompleteBoughtProducts);

        List<Product> completeBoughtProducts = incompleteBoughtProducts.stream().map(
                incompleteProduct -> {
                    Product completeProduct = productRepository.findById(incompleteProduct.getID())
                            .orElseThrow(() -> new ProductNotFoundException("product not found"));

                    int newStock = completeProduct.getStock() - incompleteProduct.getStock();
                    if (newStock < 0) {
                        throw new NotEnoughStockException("not enough stock");
                    }
                    completeProduct.setStock(newStock);

                    System.out.println(completeProduct.getStock());

                    return completeProduct;
                }).toList();

        List<ProductBuy> productBuys = new ArrayList<>();
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

            productBuyRepository.save(productBuy);

            productBuys.add(productBuy);
        }
    }

    public List<ProductBuy> getProductBuysByBuy(Buy buy) {
        return productBuyRepository.findAllByBuy(buy);
    }
}
