package com.example.starshipShop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
}
