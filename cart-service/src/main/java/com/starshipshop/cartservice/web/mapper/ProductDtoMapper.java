package com.starshipshop.cartservice.web.mapper;

import com.starshipshop.cartservice.domain.Product;
import com.starshipshop.cartservice.web.request.ProductRequest;
import org.mapstruct.Mapper;

import java.util.HashMap;

@Mapper(componentModel = "spring", uses = {})
public interface ProductDtoMapper {

    HashMap<String, Product> mapToProducts(HashMap<String, ProductRequest> products);
    Product mapToProduct(ProductRequest product);
}
