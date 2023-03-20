package com.starshipshop.cartservice.domain.service;

import com.starshipshop.cartservice.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartService {

    final CartRepository cartRepository;
}
