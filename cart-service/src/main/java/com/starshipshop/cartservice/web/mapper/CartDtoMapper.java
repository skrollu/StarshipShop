package com.starshipshop.cartservice.web.mapper;

import com.starshipshop.cartservice.domain.Cart;
import com.starshipshop.cartservice.web.response.CartResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CartDtoMapper {

    CartResponse mapToCartResponse(Cart cart);
}
