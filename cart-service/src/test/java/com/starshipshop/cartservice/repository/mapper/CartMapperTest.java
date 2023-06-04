package com.starshipshop.cartservice.repository.mapper;

import com.starshipshop.cartservice.domain.Cart;
import com.starshipshop.cartservice.domain.Product;
import com.starshipshop.cartservice.repository.jpa.CartJpa;
import com.starshipshop.cartservice.repository.jpa.ProductJpa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class CartMapperTest {

    @Test
    void mapToCart_givesACompleteCart() {
        CartJpa cartJpa = CartJpa.builder().id(1L).state("CREATED").userId("1234").build();
        List list = new ArrayList<>();
        ProductJpa productJpa1 = ProductJpa.builder().cart(cartJpa).skuCode("123").id(1L).quantity(3).build();
        ProductJpa productJpa2 = ProductJpa.builder().cart(cartJpa).skuCode("234").id(2L).quantity(2).build();
        list.add(productJpa1);
        list.add(productJpa2);
        cartJpa.setProducts(list);
        ProductMapper productMapper = mock(ProductMapper.class);
        when(productMapper.mapToProduct(productJpa1)).thenReturn(Product.builder().skuCode("123").id(1L).quantity(3).build());
        when(productMapper.mapToProduct(productJpa1)).thenReturn(Product.builder().skuCode("234").id(2L).quantity(2).build());
        CartMapper instance = new CartMapperImpl();

        Cart result = instance.mapToCart(cartJpa);

        assertThat(result.getId()).isEqualTo(1L);
        // assertThat(result.getProducts()).isNotNull(); // TODO
    }

    @Test
    void mapToCartJpa() {
    }
}