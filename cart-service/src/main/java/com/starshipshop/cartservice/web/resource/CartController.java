package com.starshipshop.cartservice.web.resource;

import com.starshipshop.cartservice.domain.service.CartService;
import com.starshipshop.cartservice.web.response.CartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

    final CartService cartService;

    @GetMapping
    ResponseEntity<CartResponse> getCart() {
        return null;
    }
}
