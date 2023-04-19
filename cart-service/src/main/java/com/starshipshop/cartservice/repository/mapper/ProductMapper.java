package com.starshipshop.cartservice.repository.mapper;

import com.starshipshop.cartservice.domain.Product;
import com.starshipshop.cartservice.repository.jpa.ProductJpa;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mapper(componentModel = "spring", uses = {})
public interface ProductMapper {

    default HashMap<String, Product> mapToProducts(List<ProductJpa> products) {
        HashMap<String, Product> result = new HashMap<>();
        for (ProductJpa p : products) {
            result.put(p.getSkuCode(), mapToProduct(p));
        }
        return result;
    }

    default List<ProductJpa> mapToProductsJpa(HashMap<String, Product> products) {
        List<ProductJpa> result = new ArrayList<>();
        products.forEach((key, value) -> {
            result.add(mapToProductJpa(value));
        });
        return result;
    }

    ProductJpa mapToProductJpa(Product product);

    Product mapToProduct(ProductJpa productJpa);
}
