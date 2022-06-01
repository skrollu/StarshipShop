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
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.starshipShop.assembler.WeaponAssembler;
import com.example.starshipShop.jpa.Weapon;
import com.example.starshipShop.requestDto.WeaponRequestDTO;
import com.example.starshipShop.service.WeaponService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/weapons")
public class WeaponController {

	@Autowired
	private WeaponService weaponService;
	@Autowired
	private WeaponAssembler assembler;

	@GetMapping
	public CollectionModel<EntityModel<Weapon>> getWeapons() {
		List<EntityModel<Weapon>> result = weaponService.getWeapons()
														.stream()
														.map(assembler::toModelWithSelfLink)
														.collect(Collectors.toList());
		return CollectionModel.of(result, linkTo(methodOn(WeaponController.class).getWeapons()).withSelfRel());
	}

	@GetMapping("/{id}")
	public EntityModel<Weapon> getWeaponById(@PathVariable Long id) {
		Weapon weapon = weaponService	.getWeaponById(id)
										.get();
		EntityModel<Weapon> result = this.assembler.toModel(weapon);
		return result;
	}

	@PostMapping
	public ResponseEntity<EntityModel<Weapon>> createWeapon(@RequestBody WeaponRequestDTO weapon) {
		EntityModel<Weapon> entityModel = assembler.toModel(this.weaponService.saveWeapon(weapon));
		return ResponseEntity	.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
													.toUri()) //
								.body(entityModel);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<Weapon>> updateWeapon(@PathVariable Long id,
			@RequestBody WeaponRequestDTO weaponRequestDTO) {
		EntityModel<Weapon> entityModel = assembler.toModel(this.weaponService.updateWeapon(id, weaponRequestDTO));
		return ResponseEntity //
								.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
													.toUri()) //
								.body(entityModel);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteWeapon(@PathVariable Long id) {
		this.weaponService.deleteWeapon(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
