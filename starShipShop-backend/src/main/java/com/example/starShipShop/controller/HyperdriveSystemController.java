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

import com.example.starshipShop.jpa.HyperdriveSystem;
import com.example.starshipShop.requestDto.HyperdriveSystemRequestDTO;
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
		HyperdriveSystem result = hyperdriveSystemService	.getHyperdriveSystemById(id)
															.get();
		return result;
	}

	@PostMapping
	public HyperdriveSystem createHyperdriveSystem(@RequestBody HyperdriveSystemRequestDTO hyperdriveSystem) {
		return this.hyperdriveSystemService.saveHyperdriveSystem(hyperdriveSystem);
	}

	@PutMapping("/{id}")
	public ResponseEntity<HyperdriveSystem> updateHyperdriveSystem(@PathVariable Long id,
			@RequestBody HyperdriveSystemRequestDTO hyperdriveSystemRequestDTO) {
		HyperdriveSystem response = this.hyperdriveSystemService.updateHyperdriveSystem(id, hyperdriveSystemRequestDTO);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteHyperdriveSystem(@PathVariable Long id) {
		this.hyperdriveSystemService.deleteHyperdriveSystem(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}