package com.starshipshop.cartservice.domain;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    @Test
    void construct_withoutUser_givesError() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Cart("", new HashMap<>()));
        assertThat(ex.getMessage()).isEqualTo("Cannot create cart without giving userId");
    }

    @Test
    void construct_givesCart() {
        Cart result = new Cart("123", new HashMap<>());
        assertThat(result.getUserId()).isEqualTo("123");
        assertThat(result.getProducts()).isEqualTo(new HashMap<>());
        assertThat(result.getState()).isEqualTo(State.CREATED);
        assertThat(result.getId()).isNull();
    }

    @Test
    void complete_givesAClosedCart() {
        Cart result = new Cart("123", new HashMap<>());
        result.complete();
        assertThat(result.getState()).isEqualTo(State.CLOSED);
    }

    @Test
    void addProducts_toAnEmptyCart_givesTheGivenProduct() {
        Cart result = new Cart("123", new HashMap<>());
        result.addProduct(new Product(1L, "123", 3));
        assertThat(result.getProducts().get("123")).isEqualTo(new Product(1L, "123", 3));
    }

    @Test
    void addProducts_aProductAlreadyInTheCart_givesTheGivenProduct() {
        HashMap products = new HashMap<>();
        products.put("123", new Product(1L, "123", 3));
        Cart result = new Cart("123", products);
        result.addProduct(new Product(1L, "123", 4));
        assertThat(result.getProducts().get("123")).isEqualTo(new Product(1L, "123", 4));
    }

    @Test
    void addProduct_aProductNotInTheCart_givesTheGivenProductAndPreviousOnes() {
        HashMap products = new HashMap<>();
        products.put("123", new Product(1L, "123", 3));
        products.put("234", new Product(2L, "234", 3));
        Cart result = new Cart("123", products);
        result.addProduct(new Product(3L, "345", 4));
        assertThat(result.getProducts().get("123")).isEqualTo(new Product(1L, "123", 3));
        assertThat(result.getProducts().get("234")).isEqualTo(new Product(2L, "234", 3));
        assertThat(result.getProducts().get("345")).isEqualTo(new Product(3L, "345", 4));
    }

    @Test
    void removeProduct_aProductInTheCart_isNoLongerInTheCart() {
        HashMap products = new HashMap<>();
        products.put("123", new Product(1L, "123", 3));
        products.put("234", new Product(2L, "234", 3));
        Cart result = new Cart("123", products);
        result.removeProduct(new Product(1L, "123", 3));
        assertThat(result.getProducts().get("123")).isNull();
    }

    @Test
    void removeProduct_aProductNotInTheCart_doNothing() {
        HashMap products = new HashMap<>();
        products.put("123", new Product(1L, "123", 3));
        products.put("234", new Product(2L, "234", 3));
        Cart result = new Cart("123", products);
        result.removeProduct(new Product(1L, "111", 3));
        assertThat(result.getProducts().get("111")).isNull();
        assertThat(result.getProducts().get("123")).isEqualTo(new Product(1L, "123", 3));
        assertThat(result.getProducts().get("234")).isEqualTo(new Product(2L, "234", 3));
    }
}