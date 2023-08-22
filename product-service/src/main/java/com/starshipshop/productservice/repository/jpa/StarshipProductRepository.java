package com.starshipshop.productservice.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StarshipProductRepository extends PagingAndSortingRepository<StarshipProductJpa, Long> {

    Optional<StarshipProductJpa> findBySkuCode(String skuCode);

}