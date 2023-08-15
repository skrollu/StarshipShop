package com.starshipshop.starshipservice.adapter;

import com.starshipshop.starshipservice.common.exception.ResourceNotFoundException;
import com.starshipshop.starshipservice.domain.Starship;
import com.starshipshop.starshipservice.repository.StarshipRepository;
import com.starshipshop.starshipservice.repository.jpa.StarshipJpa;
import com.starshipshop.starshipservice.repository.mapper.StarshipMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StarshipAdapterImpl implements StarshipAdapter {

    final StarshipRepository starshipRepository;
    final StarshipMapper starshipMapper;

    @Override
    public List<Starship> getStarshipByIdIn(List<Long> ids) {
        return starshipRepository.findByIdIn(ids).stream().map(starshipMapper::mapToStarship).collect(Collectors.toList());
    }

    @Override
    public Starship getStarshipById(Long id) {
        StarshipJpa jpa = starshipRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("No starship found with the given id ", id)));
        return starshipMapper.mapToStarship(jpa);
    }
}
