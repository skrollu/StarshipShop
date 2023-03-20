package com.starshipshop.productservice.repository.jpa;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StarshipProductRepository extends PagingAndSortingRepository<StarshipProduct, Long> {

    Optional<StarshipProduct> findBySkuCode(String skuCode);

}