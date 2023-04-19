package com.starshipshop.cartservice.service;

import com.starshipshop.cartservice.domain.Cart;
import com.starshipshop.cartservice.domain.Product;

import java.security.Principal;
import java.util.HashMap;

public interface CartService {

    Cart getCart(Principal principal);
    Cart createCart(Principal principal, HashMap<String, Product> products);
    Cart addProduct(Principal principal, Product product);
    Cart removeProduct(Principal principal, Product product);
    Cart completeCart(Principal principal);
}
