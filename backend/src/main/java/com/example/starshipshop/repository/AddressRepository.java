package com.example.starshipshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.starshipshop.repository.jpa.user.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
