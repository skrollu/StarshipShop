package com.starshipshop.starshipservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.starshipshop.starshipservice.repository.jpa.Starship;

@Repository
public interface StarshipRepository extends JpaRepository<Starship, Long> {

}
