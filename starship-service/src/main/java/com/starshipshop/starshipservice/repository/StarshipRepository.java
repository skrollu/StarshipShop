package com.starshipshop.starshipservice.repository;

import com.starshipshop.starshipservice.repository.jpa.StarshipJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StarshipRepository extends JpaRepository<StarshipJpa, Long> {

    List<StarshipJpa> findByIdIn(List<Long> id);
}
