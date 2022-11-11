package com.example.starshipshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.starshipshop.repository.jpa.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByPseudo(String pseudo);
}
