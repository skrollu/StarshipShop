package com.example.starshipShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.starshipShop.repository.jpa.user.Telephone;

@Repository
public interface TelephoneRepository extends JpaRepository<Telephone, Long> {

}
