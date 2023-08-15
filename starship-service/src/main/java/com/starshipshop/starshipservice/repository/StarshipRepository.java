package com.starshipshop.starshipservice.repository;

import com.starshipshop.starshipservice.repository.jpa.StarshipJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StarshipRepository extends JpaRepository<StarshipJpa, Long> {

    /**
     * @param id
     * @return the list of {@link StarshipJpa} that match the give ids
     */
    @NonNull
    List<StarshipJpa> findByIdIn(@NonNull List<Long> id);

    /**
     * @param id
     * @return the list of {@link StarshipJpa} that match the give ids
     */
    Optional<StarshipJpa> findById(@NonNull Long id);
}
