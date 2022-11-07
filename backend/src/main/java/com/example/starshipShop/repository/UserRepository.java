package com.example.starshipShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.starshipShop.repository.jpa.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByPseudo(String pseudo);
}
