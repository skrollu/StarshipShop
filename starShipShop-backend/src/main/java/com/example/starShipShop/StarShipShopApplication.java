package com.example.starshipShop;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.starshipShop.jpa.HyperdriveSystem;
import com.example.starshipShop.jpa.Manufacturer;
import com.example.starshipShop.jpa.Starship;
import com.example.starshipShop.jpa.Weapon;
import com.example.starshipShop.repository.HyperdriveSystemRepository;
import com.example.starshipShop.repository.ManufacturerRepository;
import com.example.starshipShop.repository.StarshipRepository;
import com.example.starshipShop.repository.WeaponRepository;

@SpringBootApplication
public class StarshipShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarshipShopApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(ManufacturerRepository manufacturerRepository, StarshipRepository starshipRepository,
			HyperdriveSystemRepository hyperdriveSystemRepository, WeaponRepository weaponRepository) {

		return args -> {
			// Y WING
			Manufacturer manufacturer = new Manufacturer(null, "Koensayr Manufacturing", null, null, null);
			HyperdriveSystem hyperdriveSystem = new HyperdriveSystem(null, "0000000001",
					"Koensayr Class 1 R-300-H Hyperdrive", manufacturer, null);
			Weapon weapon1 = new Weapon(null, "0000000001", "ArMek SW-4 Ion Cannons (on rotating turret)", manufacturer,
					null);
			Weapon weapon2 = new Weapon(null, "0000000002", "Taim & Bak IX4 Laser Cannons", manufacturer, null);
			Weapon weapon3 = new Weapon(null, "0000000003", "Arakyd Flex tube Proton Torpedo Launchers", manufacturer,
					null);
			List<Weapon> weapons = Arrays.asList(weapon1, weapon2, weapon3);

			Starship starship = new Starship(null, "0000000001", "BTL-A4 Y-Wing Starfighter",
					"Koensayr R200 Ion Jet Engines", 7, 30, 61, 2.52, "Known as a rebel bomber ship", manufacturer,
					hyperdriveSystem, weapons);

			manufacturerRepository.save(manufacturer);
			hyperdriveSystemRepository.save(hyperdriveSystem);
			weaponRepository.saveAll(weapons);
			starshipRepository.save(starship);

			// A WING
			Manufacturer manufacturer2 = new Manufacturer(null, "Kuat System Engineering", null, null, null);
			HyperdriveSystem hyperdriveSystem2 = new HyperdriveSystem(null, "0000000002",
					"Icom GBK-785 hyperdrive unit", manufacturer2, null);
			Weapon weapon4 = new Weapon(null, "0000000004", "Borstel RG-9 Laser Cannons", manufacturer2, null);
			Weapon weapon5 = new Weapon(null, "0000000005", "Dymek HM-6 concussion Missile Launcher", manufacturer2,
					null);
			List<Weapon> weapons2 = Arrays.asList(weapon4, weapon5);

			Starship starship2 = new Starship(null, "0000000002", "A-Wing Starfighter", "Novaldex J-77 Event Horizon",
					0, 0, 9.6, 0, "Known as a rebel star fighter", manufacturer2, hyperdriveSystem2, weapons2);

			manufacturerRepository.save(manufacturer2);
			hyperdriveSystemRepository.save(hyperdriveSystem2);
			weaponRepository.saveAll(weapons2);
			starshipRepository.save(starship2);

		};
	}
}
