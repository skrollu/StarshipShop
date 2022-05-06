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

import com.example.starshipShop.jpa.Starship;
import com.example.starshipShop.requestDto.StarshipRequestDTO;
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
		Starship result = starshipService	.getStarshipById(id)
											.get();
		return result;
	}

	@PostMapping
	public Starship createStarship(@RequestBody StarshipRequestDTO starship) {
		return this.starshipService.saveStarship(starship);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Starship> updateStarship(@PathVariable Long id,
			@RequestBody StarshipRequestDTO starshipRequestDTO) {
		Starship response = this.starshipService.updateStarship(id, starshipRequestDTO);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteStarship(@PathVariable Long id) {
		this.starshipService.deleteStarship(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
