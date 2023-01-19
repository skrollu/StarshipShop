package com.starshipshop.starshipservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.starshipshop.starshipservice.repository.jpa.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByPseudo(String pseudo);
}
