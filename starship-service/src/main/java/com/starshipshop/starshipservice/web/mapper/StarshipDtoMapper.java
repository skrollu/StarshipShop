package com.starshipshop.starshipservice.web.mapper;

import com.starshipshop.starshipservice.domain.Starship;
import com.starshipshop.starshipservice.web.response.StarshipOutputDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface StarshipDtoMapper {

    StarshipOutputDto mapToStarshipOutputDto(Starship starship);
}
