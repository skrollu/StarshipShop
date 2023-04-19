package com.starshipshop.cartservice.domain;


import lombok.Data;

import java.util.HashMap;

@Data
public class Cart {
    private Long id;
    private String userId;
    private State state;
    private HashMap<String, Product> products;

    public Cart(String userId, HashMap<String, Product> products) throws IllegalArgumentException {
        if (userId == null || userId.isEmpty())
            throw new IllegalArgumentException("Cannot create cart without giving userId");
        this.userId = userId;
        this.products = products;
        this.state = State.CREATED;
    }

    public void complete() {
        this.state = State.CLOSED;
    }

    public void addProduct(Product product) {
        this.products.put(product.getSkuCode(), product);
    }

    public void removeProduct(Product product) {
        this.products.remove(product.getSkuCode());
    }
}
