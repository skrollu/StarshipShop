package com.example.starshipShop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StarshipShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarshipShopApplication.class, args);
		System.out.println("Application started with the following arguments: \n" );
		for(String s: args) {
			System.out.println("=> " + s + "\n");
		}
	}
}
