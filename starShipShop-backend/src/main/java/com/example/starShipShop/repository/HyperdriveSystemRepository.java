package com.example.starshipShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.starshipShop.jpa.HyperdriveSystem;

@Repository
public interface HyperdriveSystemRepository extends JpaRepository<HyperdriveSystem, Long> {

}
