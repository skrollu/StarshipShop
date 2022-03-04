package com.example.starshipShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.starshipShop.jpa.Weapon;

@Repository
public interface WeaponRepository extends JpaRepository<Weapon, Long> {

}
