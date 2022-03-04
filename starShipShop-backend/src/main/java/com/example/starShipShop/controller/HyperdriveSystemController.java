package com.example.starshipShop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.starshipShop.jpa.HyperdriveSystem;
import com.example.starshipShop.repository.HyperdriveSystemRepository;

@RestController
@RequestMapping("/api/v1/hyperdriveSystems")
public class HyperdriveSystemController {

	@Autowired
	private HyperdriveSystemRepository hyperdriveSystemRepository;

	public HyperdriveSystemController(HyperdriveSystemRepository hyperdriveSystemRepository) {
		super();
		this.hyperdriveSystemRepository = hyperdriveSystemRepository;
	}

	@GetMapping
	public List<HyperdriveSystem> getHyperdriveSystems() {
		List<HyperdriveSystem> result = hyperdriveSystemRepository.findAll();
		System.out.println("result: " + result);
		return result;
	}

	@GetMapping("/{id}")
	public HyperdriveSystem getHyperdriveSystemById(@PathVariable Long id) {
		HyperdriveSystem result = hyperdriveSystemRepository.findById(id)
															.get();
		return result;
	}

}
