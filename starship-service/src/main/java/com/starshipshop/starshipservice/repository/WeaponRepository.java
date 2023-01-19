package com.starshipshop.starshipservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.starshipshop.starshipservice.repository.jpa.Weapon;

@Repository
public interface WeaponRepository extends JpaRepository<Weapon, Long> {

}
