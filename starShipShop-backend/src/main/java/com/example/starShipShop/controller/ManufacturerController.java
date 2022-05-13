package com.example.starshipShop.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.starshipShop.assembler.ManufacturerAssembler;
import com.example.starshipShop.jpa.Manufacturer;
import com.example.starshipShop.requestDto.ManufacturerRequestDTO;
import com.example.starshipShop.service.ManufacturerService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/manufacturers")
public class ManufacturerController {

	@Autowired
	private ManufacturerService manufacturerService;
	@Autowired
	private ManufacturerAssembler manufacturerAssembler;

	@GetMapping
	public CollectionModel<EntityModel<Manufacturer>> getManufacturers() {
		List<EntityModel<Manufacturer>> result = manufacturerService.getManufacturers()
																	.stream()
																	.map(manufacturerAssembler::toModelWithSelfLink) //
																	.collect(Collectors.toList());
		return CollectionModel.of(result,
				linkTo(methodOn(ManufacturerController.class).getManufacturers()).withSelfRel());
	}

	@GetMapping("/{id}")
	public EntityModel<Manufacturer> getManufacturerById(@PathVariable Long id) {
		Manufacturer manufacturer = manufacturerService	.getManufacturerById(id)
														.get();

		EntityModel<Manufacturer> result = this.manufacturerAssembler.toModel(manufacturer);
		return result;
	}

	@GetMapping("/name/{name}")
	public Manufacturer getManufacturerByName(@PathVariable String name) {
		Manufacturer result = manufacturerService	.getManufacturerByName(name)
													.get();
		return result;
	}

	@PostMapping
	public Manufacturer createManufacturer(@RequestBody ManufacturerRequestDTO manufacturer) {
		return this.manufacturerService.saveManufacturer(manufacturer);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Manufacturer> updateManufacturer(@PathVariable Long id,
			@RequestBody ManufacturerRequestDTO manufacturerRequestDTO) {
		Manufacturer response = this.manufacturerService.updateManufacturer(id, manufacturerRequestDTO);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteManufacturer(@PathVariable Long id) {
		this.manufacturerService.deleteManufacturer(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

}
