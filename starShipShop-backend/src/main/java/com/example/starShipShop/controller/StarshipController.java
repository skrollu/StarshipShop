package com.example.starshipShop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.starshipShop.jpa.Starship;
import com.example.starshipShop.repository.StarshipRepository;

@RestController
@RequestMapping("/api/v1/starships")
public class StarshipController {

	@Autowired
	private StarshipRepository starshipRepository;

	public StarshipController(StarshipRepository starshipRepository) {
		super();
		this.starshipRepository = starshipRepository;
	}

	@GetMapping
	public List<Starship> getStarships() {
		List<Starship> result = starshipRepository.findAll();
		System.out.println("result: " + result);
		return result;
	}

	@GetMapping("/{id}")
	public Starship getStarshipById(@PathVariable Long id) {
		Starship result = starshipRepository.findById(id)
											.get();
		return result;
	}
}
