package com.starshipshop.starshipservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.starshipshop.starshipservice.repository.jpa.Manufacturer;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

	Optional<Manufacturer> findByName(String name);
}
