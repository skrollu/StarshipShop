package com.example.starshipShop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.starshipShop.jpa.Starship;
import com.example.starshipShop.service.StarshipService;

@RestController
@RequestMapping("/api/v1/starships")
public class StarshipController {

	@Autowired
	private StarshipService starshipService;

	public StarshipController(StarshipService starshipService) {
		super();
		this.starshipService = starshipService;
	}

	@GetMapping
	public List<Starship> getStarships() {
		List<Starship> result = starshipService.getStarships();
		return result;
	}

	@GetMapping("/{id}")
	public Starship getStarshipById(@PathVariable Long id) {
		Starship result = starshipService.getStarship(id)
											.get();
		return result;
	}
}
