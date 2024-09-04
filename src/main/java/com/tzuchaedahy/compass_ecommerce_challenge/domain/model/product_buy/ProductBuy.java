package com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product_buy;

import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.buy.Buy;
import com.tzuchaedahy.compass_ecommerce_challenge.domain.model.product.Product;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_buy")
public class ProductBuy {
    @EmbeddedId
    private ProductBuyKey id;

    @ManyToOne
    @MapsId("productID")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("buyID")
    @JoinColumn(name = "buy_id")
    private Buy buy;

    private Float priceAtMoment;
    private Integer quantity;

    public ProductBuy() {
    }

    public ProductBuyKey getId() {
        return id;
    }

    public void setId(ProductBuyKey id) {
        this.id = id;
    }

    public Float getPriceAtMoment() {
        return priceAtMoment;
    }

    public void setPriceAtMoment(Float priceAtMoment) {
        this.priceAtMoment = priceAtMoment;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Buy getBuy() {
        return buy;
    }

    public void setBuy(Buy buy) {
        this.buy = buy;
    }

}
