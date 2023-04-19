package com.starshipshop.cartservice.adapter;

import com.starshipshop.cartservice.domain.Cart;

public interface CartAdapter {

    Cart getCart(String userId);
    Cart saveCart(Cart cart);
}
