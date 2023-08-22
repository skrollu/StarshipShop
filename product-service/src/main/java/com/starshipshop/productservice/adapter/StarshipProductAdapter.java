package com.starshipshop.productservice.adapter;

import com.starshipshop.productservice.domain.model.StarshipProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StarshipProductAdapter {
    Page<StarshipProduct> findPage(Pageable paging);

    StarshipProduct findBySkuCode(String skuCode);
}
