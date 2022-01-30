package com.example.starshipShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.starshipShop.jpa.Starship;

@Repository
public interface StarshipRepository extends JpaRepository<Starship, Long> {

}
