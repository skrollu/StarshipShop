package com.example.starshipShop;

import java.util.HashSet;
import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.starshipShop.repository.AccountRepository;
import com.example.starshipShop.repository.AddressRepository;
import com.example.starshipShop.repository.EmailRepository;
import com.example.starshipShop.repository.TelephoneRepository;
import com.example.starshipShop.repository.UserRepository;
import com.example.starshipShop.repository.jpa.user.Account;
import com.example.starshipShop.repository.jpa.user.Address;
import com.example.starshipShop.repository.jpa.user.Email;
import com.example.starshipShop.repository.jpa.user.Telephone;
import com.example.starshipShop.repository.jpa.user.User;
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
	
	@Bean
	CommandLineRunner commandLineRunner(EmailRepository emailRepository,
	TelephoneRepository telephoneRepository, AddressRepository addressRepository,
	UserRepository userRepository, AccountRepository accountRepository,
	PasswordEncoder encoder) {
		return args -> {
			// Account account = Account.builder().username("user").password(encoder.encode("password")).roles("USER").build();
			// accountRepository.save(account);
			// accountRepository.save(Account.builder().username("admin").password(encoder.encode("password")).roles("USER, ADMIN").build());		
			
			// Email email = Email.builder().email("mail@mail.com").build();
			// Set<Email> emails = new HashSet<>();
			// emails.add(email);

			// Telephone telephone = Telephone.builder().telephoneNumber("0123456789").build();
			// Set<Telephone> telephones = new HashSet<>();
			// telephones.add(telephone);

			// Address address =  Address.builder()
			// .address("23 Baker Court")
			// .zipCode(11224L)
			// .city("Brooklyn")
			// .state("NY")
			// .country("USA")
			// .planet("Earth")
			// .build();
			// Set<Address> addresses = new HashSet<>();
			// addresses.add(address);

			// User user = User.builder().pseudo("john").account(account).emails(emails).addresses(addresses).telephones(telephones).build();
			// userRepository.save(user);
		};
	}
}
