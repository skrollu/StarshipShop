package com.example.starshipShop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.starshipShop.jpa.Weapon;
import com.example.starshipShop.service.WeaponService;

@RestController
@RequestMapping("/api/v1/weapons")
public class WeaponController {

	@Autowired
	private WeaponService weaponService;

	public WeaponController(WeaponService weaponService) {
		super();
		this.weaponService = weaponService;
	}

	@GetMapping
	public List<Weapon> getWeapons() {
		List<Weapon> result = weaponService.getWeapons();
		return result;
	}

	@GetMapping("/{id}")
	public Weapon getWeaponById(@PathVariable Long id) {
		Weapon result = weaponService	.getWeaponById(id)
										.get();
		return result;
	}
}
