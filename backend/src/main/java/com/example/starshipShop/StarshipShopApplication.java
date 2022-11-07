package com.example.starshipshop;

import java.util.HashSet;
import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.starshipshop.repository.AccountRepository;
import com.example.starshipshop.repository.AddressRepository;
import com.example.starshipshop.repository.EmailRepository;
import com.example.starshipshop.repository.TelephoneRepository;
import com.example.starshipshop.repository.UserRepository;
import com.example.starshipshop.repository.jpa.user.Account;
import com.example.starshipshop.repository.jpa.user.Address;
import com.example.starshipshop.repository.jpa.user.Email;
import com.example.starshipshop.repository.jpa.user.Telephone;
import com.example.starshipshop.repository.jpa.user.User;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class StarshipShopApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(StarshipShopApplication.class, args);
		log.warn("Application started with the following arguments:");
		for (String s : args) {
			log.warn("=> " + s);
		}
	}
}
