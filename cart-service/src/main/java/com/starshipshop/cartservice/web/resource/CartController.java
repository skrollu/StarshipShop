package com.starshipshop.cartservice.web.resource;

import com.starshipshop.cartservice.domain.service.CartService;
import com.starshipshop.cartservice.web.response.CartResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

    final CartService cartService;

    @GetMapping
    CartResponse getCart(Principal principal) {
        log.info(principal.toString());
        CartResponse result = cartService.getCart(principal);
        log.info("Result: " + result);
        return result;
    }
}
