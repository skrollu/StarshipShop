package com.starshipshop.cartservice.domain.service;

import com.starshipshop.cartservice.repository.CartRepository;
import com.starshipshop.cartservice.repository.jpa.Cart;
import com.starshipshop.cartservice.web.response.CartResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartService {

    final CartRepository cartRepository;

    public CartResponse getCart(Principal principal) {

        String principalId = principal.getName();
        log.info("CartService: " + principalId);
        Optional<Cart> optCart = cartRepository.findByUserId(principalId);
        if (optCart.isEmpty()) {
            return null;
        } else {
            log.info("Cart: " + optCart.get());
            return new CartResponse(optCart.get().getState());
        }
    }
}
