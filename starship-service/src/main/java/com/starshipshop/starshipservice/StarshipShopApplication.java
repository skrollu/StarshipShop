package com.starshipshop.starshipservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories(enableDefaultTransactions = false)
public class StarshipShopApplication {

	public static void main(String[] args) { SpringApplication.run(StarshipShopApplication.class, args); }
}
