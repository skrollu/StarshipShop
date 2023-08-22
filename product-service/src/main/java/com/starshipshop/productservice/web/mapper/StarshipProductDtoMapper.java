package com.starshipshop.productservice.web.mapper;

import com.starshipshop.productservice.domain.model.StarshipProduct;
import com.starshipshop.productservice.web.response.StarshipProductResponse;
import org.mapstruct.Mapper;

@Mapper
public interface StarshipProductDtoMapper {

    StarshipProductResponse mapToStarshipProductResponse(StarshipProduct starshipProduct);
}
