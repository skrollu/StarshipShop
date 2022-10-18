package com.example.starshipShop.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.starshipShop.repository.jpa.HyperdriveSystem;

@Repository
public interface HyperdriveSystemRepository extends JpaRepository<HyperdriveSystem, Long> {
	Optional<HyperdriveSystem> findByName(String name);
}
