package com.example.starshipshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.starshipshop.repository.jpa.Weapon;

@Repository
public interface WeaponRepository extends JpaRepository<Weapon, Long> {

}
