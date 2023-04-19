package com.starshipshop.cartservice.adapter;

import com.starshipshop.cartservice.domain.Cart;
import com.starshipshop.cartservice.repository.CartRepository;
import com.starshipshop.cartservice.repository.jpa.CartJpa;
import com.starshipshop.cartservice.repository.mapper.CartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CartAdapterImpl implements CartAdapter {

    final CartRepository cartRepository;
    final CartMapper cartMapper;

    @Override
    public Cart getCart(String userId) {
        return cartRepository.findByUserId(userId)
                .map(cartMapper::mapToCart)
                .orElse(null);
    }

    @Override
    public Cart saveCart(Cart cart) {
        CartJpa cartJpa = cartMapper.mapToCartJpa(cart);
        cartJpa = cartRepository.save(cartJpa);
        Cart result = cartMapper.mapToCart(cartJpa);
        return result;
    }
}
