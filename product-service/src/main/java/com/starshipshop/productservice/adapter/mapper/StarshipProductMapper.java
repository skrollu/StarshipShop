package com.starshipshop.productservice.adapter.mapper;

import com.starshipshop.productservice.client.response.InventoryResponse;
import com.starshipshop.productservice.client.response.StarshipResponse;
import com.starshipshop.productservice.domain.model.StarshipProduct;
import com.starshipshop.productservice.repository.jpa.StarshipProductJpa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface StarshipProductMapper {

    @Mapping(source = "spJpa.skuCode", target = "skuCode")
    public StarshipProduct mapToStarshipProduct(StarshipResponse sr, InventoryResponse ir, StarshipProductJpa spJpa);
}
