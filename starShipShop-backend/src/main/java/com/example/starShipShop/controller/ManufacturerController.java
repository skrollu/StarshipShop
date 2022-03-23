package com.example.starshipShop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.starshipShop.jpa.Manufacturer;
import com.example.starshipShop.service.ManufacturerService;

@RestController
@RequestMapping("/api/v1/manufacturers")
public class ManufacturerController {

	@Autowired
	private ManufacturerService manufacturerService;

	public ManufacturerController(ManufacturerService manufacturerService) {
		super();
		this.manufacturerService = manufacturerService;
	}

	@GetMapping
	public List<Manufacturer> getManufacturers() {
		List<Manufacturer> result = manufacturerService.getManufacturers();
		return result;
	}

	@GetMapping("/{id}")
	public Manufacturer getManufacturerById(@PathVariable Long id) {
		Manufacturer result = manufacturerService	.getManufacturer(id)
													.get();
		return result;
	}
}
