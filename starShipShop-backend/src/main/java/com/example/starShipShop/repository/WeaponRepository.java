package com.example.starshipShop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.starshipShop.jpa.Weapon;

@Repository
public interface WeaponRepository extends JpaRepository<Weapon, Long> {
	Optional<Weapon> findByName(String name);

}
