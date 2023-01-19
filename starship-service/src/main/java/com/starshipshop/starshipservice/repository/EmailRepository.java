package com.starshipshop.starshipservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.starshipshop.starshipservice.repository.jpa.user.Email;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

}
