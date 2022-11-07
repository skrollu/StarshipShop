package com.example.starshipShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.starshipShop.repository.jpa.user.Email;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

}
