package com.starshipshop.cartservice.repository.mapper;

import com.starshipshop.cartservice.domain.Product;
import com.starshipshop.cartservice.repository.jpa.CartJpa;
import com.starshipshop.cartservice.repository.jpa.ProductJpa;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ProductMapperTest {

    @Test
    void mapToProducts_withAListOfProductJpa_givesAHashMapOfProduct() {
        ProductMapper instance = new ProductMapperImpl();
        ProductJpa product1 = ProductJpa.builder().id(1L)
                .skuCode("1234")
                .quantity(1)
                .build();
        ProductJpa product2 = ProductJpa.builder().id(2L)
                .skuCode("2345")
                .quantity(1)
                .build();
        List<ProductJpa> list = new ArrayList<>();
        list.add(product1);
        list.add(product2);

        HashMap<String, Product> result = instance.mapToProducts(list);

        assertThat(result.get("1234").getId()).isEqualTo(1L);
        assertThat(result.get("2345").getId()).isEqualTo(2L);
    }

    @Test
    void mapToProductsJpa_withAHashMapOfProduct_givesAListOfProductJpa() {
        ProductMapper instance = new ProductMapperImpl();

        Product product1 = Product.builder().id(1L)
                .skuCode("1234")
                .quantity(1)
                .build();
        Product product2 = Product.builder().id(2L)
                .skuCode("2345")
                .quantity(1)
                .build();
        HashMap map = new HashMap<>();
        map.put("1234", product1);
        map.put("2345", product2);
        List<ProductJpa> result = instance.mapToProductsJpa(map);

        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(1).getSkuCode()).isEqualTo("2345");
    }

    @Test
    void mapToProductJpa_product_givesCompleteProductJpa() {
        ProductMapper instance = new ProductMapperImpl();
        Product product = Product.builder().id(1L)
                .skuCode("1234")
                .quantity(1)
                .build();
        ProductJpa result = instance.mapToProductJpa(product);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getSkuCode()).isEqualTo("1234");
        assertThat(result.getQuantity()).isEqualTo(1);
    }

    @Test
    void mapToProduct_productJpa_givesCompleteProduct() {
        ProductMapper instance = new ProductMapperImpl();
        ProductJpa jpa = ProductJpa.builder()
                .id(1L)
                .skuCode("1234")
                .quantity(1)
                .cart(new CartJpa())
                .build();
        Product result = instance.mapToProduct(jpa);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getSkuCode()).isEqualTo("1234");
        assertThat(result.getQuantity()).isEqualTo(1);
    }
}