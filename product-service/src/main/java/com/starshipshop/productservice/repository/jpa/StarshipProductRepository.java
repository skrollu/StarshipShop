package com.starshipshop.productservice.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StarshipProductRepository extends JpaRepository<StarshipProduct, Long> {

    Optional<StarshipProduct> findBySkuCode(String skuCode);
}