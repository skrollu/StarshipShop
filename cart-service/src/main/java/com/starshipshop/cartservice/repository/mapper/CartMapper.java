package com.starshipshop.cartservice.repository.mapper;

import com.starshipshop.cartservice.domain.Cart;
import com.starshipshop.cartservice.repository.jpa.CartJpa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface CartMapper {

    Cart mapToCart(CartJpa cartJpa);
    CartJpa mapToCartJpa(Cart cart);
}
