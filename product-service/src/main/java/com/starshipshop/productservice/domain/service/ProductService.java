package com.starshipshop.productservice.domain.service;

import com.starshipshop.productservice.domain.model.StarshipProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    public Page<StarshipProduct> getStarshipProductFromPage(Pageable pageable);

    public StarshipProduct getStarshipProductBySkuCode(String skuCode);
}
