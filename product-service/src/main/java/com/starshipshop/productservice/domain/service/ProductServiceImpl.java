package com.starshipshop.productservice.domain.service;

import com.starshipshop.productservice.adapter.StarshipProductAdapter;
import com.starshipshop.productservice.domain.model.StarshipProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final StarshipProductAdapter starshipProductAdapter;

    public Page<StarshipProduct> getStarshipProductFromPage(Pageable pageable) {
        // TODO improve the sort to sort by field contained in the StarshipProductResponse object
        return starshipProductAdapter.findPage(pageable);
    }

    public StarshipProduct getStarshipProductBySkuCode(String skuCode) {
        return starshipProductAdapter.findBySkuCode(skuCode);
    }
}
