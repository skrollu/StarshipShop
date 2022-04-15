package com.example.starshipShop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.starshipShop.jpa.HyperdriveSystem;
import com.example.starshipShop.service.HyperdriveSystemService;

@RestController
@RequestMapping("/api/v1/hyperdriveSystems")
public class HyperdriveSystemController {

	@Autowired
	private HyperdriveSystemService hyperdriveSystemService;

	public HyperdriveSystemController(HyperdriveSystemService hyperdriveSystemService) {
		super();
		this.hyperdriveSystemService = hyperdriveSystemService;
	}

	@GetMapping
	public List<HyperdriveSystem> getHyperdriveSystems() {
		List<HyperdriveSystem> result = hyperdriveSystemService.getHyperdriveSystems();

		return result;
	}

	@GetMapping("/{id}")
	public HyperdriveSystem getHyperdriveSystemById(@PathVariable Long id) {
		HyperdriveSystem result = hyperdriveSystemService	.getHyperdriveSystem(id)
															.get();
		return result;
	}
}
