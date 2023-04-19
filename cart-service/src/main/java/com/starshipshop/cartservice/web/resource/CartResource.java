package com.starshipshop.cartservice.web.resource;

import com.starshipshop.cartservice.domain.Cart;
import com.starshipshop.cartservice.service.CartService;
import com.starshipshop.cartservice.web.mapper.CartDtoMapper;
import com.starshipshop.cartservice.web.mapper.ProductDtoMapper;
import com.starshipshop.cartservice.web.request.ProductRequest;
import com.starshipshop.cartservice.web.response.CartResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/carts")
public class CartResource {

    final CartService cartService;
    final CartDtoMapper cartDtoMapper;
    final ProductDtoMapper productDtoMapper;

    @GetMapping
    CartResponse getCart(Principal principal) {
        Cart cart = cartService.getCart(principal);
        CartResponse result = cartDtoMapper.mapToCartResponse(cart);
        return result;
    }

    @PostMapping
    CartResponse createCart(Principal principal, HashMap<String, ProductRequest> products) {
        Cart cart = cartService.createCart(principal, productDtoMapper.mapToProducts(products));
        CartResponse result = cartDtoMapper.mapToCartResponse(cart);
        return result;
    }

    @PutMapping()
    CartResponse addProduct(Principal principal, ProductRequest product) {
        Cart cart = cartService.addProduct(principal, productDtoMapper.mapToProduct(product));
        CartResponse result = cartDtoMapper.mapToCartResponse(cart);
        return result;
    }

    @DeleteMapping
    CartResponse removeProduct(Principal principal, ProductRequest product) {
        Cart cart = cartService.removeProduct(principal, productDtoMapper.mapToProduct(product));
        CartResponse result = cartDtoMapper.mapToCartResponse(cart);
        return result;
    }

    @PostMapping("/complete")
    CartResponse complete(Principal principal) {
        Cart cart = cartService.completeCart(principal);
        CartResponse result = cartDtoMapper.mapToCartResponse(cart);
        return result;
    }
}
