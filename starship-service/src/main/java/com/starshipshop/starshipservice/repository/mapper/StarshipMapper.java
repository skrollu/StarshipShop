package com.starshipshop.starshipservice.repository.mapper;

import com.starshipshop.starshipservice.domain.Starship;
import com.starshipshop.starshipservice.repository.jpa.StarshipJpa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface StarshipMapper {

    Starship mapToStarship(StarshipJpa starshipJpa);
}
