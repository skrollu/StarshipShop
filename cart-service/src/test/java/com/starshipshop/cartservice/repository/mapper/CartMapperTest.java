package com.starshipshop.cartservice.repository.mapper;

import com.starshipshop.cartservice.domain.Cart;
import com.starshipshop.cartservice.repository.jpa.CartJpa;
import com.starshipshop.cartservice.repository.jpa.ProductJpa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = {CartMapper.class, ProductMapper.class})
class CartMapperTest {

    @Autowired
    private CartMapper cartMapper;

    @Test
    void mapToCart_givesACompleteCart() {
        CartJpa jpa = CartJpa.builder().id(1L).state("CREATED").userId("1234").build();
        List list = new ArrayList<>();
        list.add(ProductJpa.builder().cart(jpa).skuCode("123").id(1L).quantity(3));
        list.add(ProductJpa.builder().cart(jpa).skuCode("234").id(2L).quantity(2));
        jpa.setProducts(list);

        Cart result = cartMapper.mapToCart(jpa);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getProducts()).isNotNull();
        System.out.println(result);
    }

    @Test
    void mapToCartJpa() {
    }
}