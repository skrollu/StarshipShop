package com.starshipshop.starshipservice.controller;

import com.starshipshop.starshipservice.controller.assembler.WeaponAssembler;
import com.starshipshop.starshipservice.domain.weapon.WeaponInput;
import com.starshipshop.starshipservice.domain.weapon.WeaponOutput;
import com.starshipshop.starshipservice.service.WeaponService;
import com.starshipshop.starshipservice.service.mapper.converter.HashToIdConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/weapons")
public class WeaponController {

	private final WeaponService weaponService;
	private final WeaponAssembler assembler;
	private final HashToIdConverter hashToIdConverter;

	@PreAuthorize("permitAll()")
	@GetMapping
	public CollectionModel<EntityModel<WeaponOutput>> getWeapons() {
		List<EntityModel<WeaponOutput>> result = weaponService.getWeaponsOutput()
				.stream()
				.map(assembler::toModelWithSelfLink)
				.collect(Collectors.toList());
		return CollectionModel.of(result, linkTo(methodOn(WeaponController.class).getWeapons()).withSelfRel());
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/{id}")
	public EntityModel<WeaponOutput> getWeaponById(@PathVariable String id) {
		return this.assembler.toModel(weaponService.getWeaponsOutputById(hashToIdConverter.convert(id)));
	}

	@PreAuthorize("hasAuthority('SCOPE_starship:write')")
	@PostMapping
	public ResponseEntity<EntityModel<WeaponOutput>> createWeapon(@RequestBody WeaponInput wi) {
		EntityModel<WeaponOutput> entityModel = assembler.toModel(this.weaponService.createWeapon(wi));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
				.toUri())
				.body(entityModel);
	}

	@PreAuthorize("hasAuthority('SCOPE_starship:write')")
	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<WeaponOutput>> updateWeapon(@PathVariable String id,
			@RequestBody WeaponInput wi) {
		EntityModel<WeaponOutput> entityModel = assembler
				.toModel(this.weaponService.updateWeapon(hashToIdConverter.convert(id), wi));
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
						.toUri())
				.body(entityModel);
	}

	@PreAuthorize("hasAuthority('SCOPE_starship:write')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteWeapon(@PathVariable String id) {
		this.weaponService.deleteWeapon(hashToIdConverter.convert(id));
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
