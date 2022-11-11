package com.example.starshipshop.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.starshipshop.controller.assembler.WeaponAssembler;
import com.example.starshipshop.domain.WeaponDto;
import com.example.starshipshop.domain.WeaponRequestInput;
import com.example.starshipshop.service.WeaponService;
import com.example.starshipshop.service.mapper.converter.HashToIdConverter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/weapons")
public class WeaponController {
	
	private final WeaponService weaponService;
	
	private final WeaponAssembler assembler;
	
	private final HashToIdConverter hashToIdConverter;
	
	@PreAuthorize("permitAll()")
	@GetMapping
	public CollectionModel<EntityModel<WeaponDto>> getWeapons() {
		List<EntityModel<WeaponDto>> result = weaponService	.getWeaponsDto()
		.stream()
		.map(assembler::toModelWithSelfLink)
		.collect(Collectors.toList());
		return CollectionModel.of(result, linkTo(methodOn(WeaponController.class).getWeapons()).withSelfRel());
	}
	
	@PreAuthorize("permitAll()")
	@GetMapping("/{id}")
	public EntityModel<WeaponDto> getWeaponById(@PathVariable String id) {
		Optional<WeaponDto> weapon = weaponService.getWeaponsDtoById(hashToIdConverter.convert(id));
		if(weapon.isPresent()){
			return this.assembler.toModel(weapon.get());
		} else {
			throw new NullPointerException();
		}
	}
	
	@PreAuthorize("hasAuthority('starship:write')")
	@PostMapping
	public ResponseEntity<EntityModel<WeaponDto>> createWeapon(@RequestBody WeaponRequestInput wri) {
		EntityModel<WeaponDto> entityModel = assembler.toModel(this.weaponService.createWeapon(wri));
		return ResponseEntity	.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
		.toUri()) 
		.body(entityModel);
	}
	
	@PreAuthorize("hasAuthority('starship:write')")
	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<WeaponDto>> updateWeapon(@PathVariable String id,
	@RequestBody WeaponRequestInput wri) {
		EntityModel<WeaponDto> entityModel = assembler.toModel(this.weaponService.updateWeapon(hashToIdConverter.convert(id), wri));
		return ResponseEntity 
		.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
		.toUri()) 
		.body(entityModel);
	}
	
	@PreAuthorize("hasAuthority('starship:write')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteWeapon(@PathVariable String id) {
		this.weaponService.deleteWeapon(hashToIdConverter.convert(id));
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
