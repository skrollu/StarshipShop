package com.starshipshop.starshipservice.adapter;

import com.starshipshop.starshipservice.domain.Starship;

import java.util.List;

public interface StarshipAdapter {

    List<Starship> getStarshipByIdIn(List<Long> ids);
}
