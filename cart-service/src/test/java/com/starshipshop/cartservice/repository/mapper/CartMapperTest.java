package com.starshipshop.cartservice.repository.mapper;

import com.starshipshop.cartservice.domain.Cart;
import com.starshipshop.cartservice.domain.Product;
import com.starshipshop.cartservice.repository.jpa.CartJpa;
import com.starshipshop.cartservice.repository.jpa.ProductJpa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class CartMapperIT {

    @Autowired
    CartMapper instance;

    @MockBean
    ProductMapper productMapper;

    @Test
    void mapToCart_givesACompleteCart() {
        when(productMapper.mapToProduct(any())).thenReturn(Product.builder().build());

        CartJpa jpa = CartJpa.builder().id(1L).state("CREATED").userId("1234").build();
        List list = new ArrayList<>();
        list.add(ProductJpa.builder().cart(jpa).skuCode("123").id(1L).quantity(3));
        list.add(ProductJpa.builder().cart(jpa).skuCode("234").id(2L).quantity(2));
        jpa.setProducts(list);

        Cart result = instance.mapToCart(jpa);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getProducts()).isNotNull();
    }

    @Test
    void mapToCartJpa() {
    }
}