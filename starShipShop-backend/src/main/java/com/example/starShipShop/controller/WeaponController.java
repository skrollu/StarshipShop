package com.example.starshipShop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.starshipShop.jpa.Weapon;
import com.example.starshipShop.repository.WeaponRepository;

@RestController
@RequestMapping("/api/v1/weapons")
public class WeaponController {

	@Autowired
	private WeaponRepository weaponRepository;

	public WeaponController(WeaponRepository weaponRepository) {
		super();
		this.weaponRepository = weaponRepository;
	}

	@GetMapping
	public List<Weapon> getWeapons() {
		List<Weapon> result = weaponRepository.findAll();
		System.out.println("result: " + result);
		return result;
	}

	@GetMapping("/{id}")
	public Weapon getWeaponById(@PathVariable Long id) {
		Weapon result = weaponRepository.findById(id)
										.get();
		return result;
	}
}
