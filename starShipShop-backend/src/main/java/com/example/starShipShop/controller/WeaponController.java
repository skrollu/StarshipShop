package com.example.starshipShop.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.starshipShop.jpa.Weapon;
import com.example.starshipShop.requestDto.WeaponRequestDTO;
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

	@PostMapping
	public Weapon createWeapon(@RequestBody WeaponRequestDTO weapon) {
		return this.weaponService.saveWeapon(weapon);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Weapon> updateWeapon(@PathVariable Long id, @RequestBody WeaponRequestDTO weaponRequestDTO) {
		Weapon response = this.weaponService.updateWeapon(id, weaponRequestDTO);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteWeapon(@PathVariable Long id) {
		this.weaponService.deleteWeapon(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
