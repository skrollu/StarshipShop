package com.example.starshipShop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.starshipShop.jpa.Weapon;
import com.example.starshipShop.repository.WeaponRepository;

import lombok.Data;

@Data
@Service
public class WeaponService {

	@Autowired
	private WeaponRepository weaponRepository;

	public List<Weapon> getWeapons() {
		List<Weapon> list = weaponRepository.findAll();
		return list;
	}

	public Optional<Weapon> getWeaponById(final Long id) {
		return weaponRepository.findById(id);
	}

	public Optional<Weapon> getWeaponByName(final String name) {
		return weaponRepository.findByName(name);
	}

	public void deleteWeapon(final Long id) {
		weaponRepository.deleteById(id);
	}

	public Weapon saveWeapon(Weapon weapon) {
		Weapon savedWeapon = weaponRepository.save(weapon);
		return savedWeapon;
	}
}
