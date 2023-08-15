package com.starshipshop.starshipservice.service;

import com.starshipshop.starshipservice.domain.Starship;

import java.util.List;

public interface StarshipService {

    List<Starship> getStarshipByIdIn(List<Long> ids);
    Starship getStarshipById(Long id);
}
