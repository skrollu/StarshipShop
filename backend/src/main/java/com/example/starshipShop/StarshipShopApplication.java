package com.example.starshipShop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.starshipShop.repository.AccountRepository;
import com.example.starshipShop.repository.jpa.Account;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class StarshipShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarshipShopApplication.class, args);
		log.warn("Application started with the following arguments:");
		for(String s: args) {
			log.warn("=> " + s);
		}
	}

	// @Bean
	// CommandLineRunner commandLineRunner(AccountRepository accountRepository, PasswordEncoder encoder) {
	// 	return args -> {
	// 		accountRepository.save(new Account(1L, "user", encoder.encode("password"), "ROLE_USER"));
	// 		accountRepository.save(new Account(2L, "admin", encoder.encode("password"), "ROLE_USER,ROLE_ADMIN"));
	// 	};
	// }
}
