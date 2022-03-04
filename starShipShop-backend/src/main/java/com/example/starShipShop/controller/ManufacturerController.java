package com.example.starshipShop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.starshipShop.jpa.Manufacturer;
import com.example.starshipShop.repository.ManufacturerRepository;

@RestController
@RequestMapping("/api/v1/manufacturers")
public class ManufacturerController {

	@Autowired
	private ManufacturerRepository manufacturerRepository;

	public ManufacturerController(ManufacturerRepository manufacturerRepository) {
		super();
		this.manufacturerRepository = manufacturerRepository;
	}

	@GetMapping
	public List<Manufacturer> getManufacturers() {
		List<Manufacturer> result = manufacturerRepository.findAll();
		System.out.println("result: " + result);
		return result;
	}

	@GetMapping("/{id}")
	public Manufacturer getManufacturerById(@PathVariable Long id) {
		Manufacturer result = manufacturerRepository.findById(id)
													.get();
		return result;
	}
}
