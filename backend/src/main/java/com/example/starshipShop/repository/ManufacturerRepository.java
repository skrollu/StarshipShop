package com.example.starshipshop.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.starshipshop.repository.jpa.Manufacturer;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

	Optional<Manufacturer> findByName(String name);
}
