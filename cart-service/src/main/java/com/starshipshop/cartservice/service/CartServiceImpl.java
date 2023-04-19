package com.starshipshop.cartservice.service;

import com.starshipshop.cartservice.adapter.CartAdapter;
import com.starshipshop.cartservice.domain.Cart;
import com.starshipshop.cartservice.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    final CartAdapter cartAdapter;

    @Override
    public Cart getCart(Principal principal) {
        String principalId = principal.getName();
        Cart result = cartAdapter.getCart(principalId);
        return result;
    }

    @Override
    public Cart createCart(Principal principal, HashMap<String, Product> products) {
        String principalId = principal.getName();
        Cart result = cartAdapter.saveCart(new Cart(principalId, products));
        return result;
    }

    @Override
    public Cart addProduct(Principal principal, Product product) {
        String principalId = principal.getName();
        Cart cart = cartAdapter.getCart(principalId);
        cart.addProduct(product);
        Cart result = cartAdapter.saveCart(cart);
        return result;
    }

    @Override
    public Cart removeProduct(Principal principal, Product product) {
        String principalId = principal.getName();
        Cart cart = cartAdapter.getCart(principalId);
        cart.removeProduct(product);
        Cart result = cartAdapter.saveCart(cart);
        return result;
    }

    @Override
    public Cart completeCart(Principal principal) {
        String principalId = principal.getName();
        Cart cart = cartAdapter.getCart(principalId);
        cart.complete();
        Cart result = cartAdapter.saveCart(cart);
        return result;
    }
}
