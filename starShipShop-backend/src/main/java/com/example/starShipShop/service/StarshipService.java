package com.example.starshipShop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.starshipShop.jpa.Starship;
import com.example.starshipShop.repository.StarshipRepository;

import lombok.Data;

@Data
@Service
public class StarshipService {

	@Autowired
	private StarshipRepository starshipRepository;

	public List<Starship> getStarships() {
		List<Starship> list = starshipRepository.findAll();
		return list;
	}

	public Optional<Starship> getStarship(final Long id) {
		return starshipRepository.findById(id);
	}

	public void deleteStarship(final Long id) {
		starshipRepository.deleteById(id);
	}

	public Starship saveStarship(Starship weapon) {
		Starship savedStarship = starshipRepository.save(weapon);
		return savedStarship;
	}
}
