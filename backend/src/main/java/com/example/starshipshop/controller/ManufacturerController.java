package com.example.starshipshop.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

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

import com.example.starshipshop.controller.assembler.ManufacturerAssembler;
import com.example.starshipshop.domain.manufacturer.ManufacturerInput;
import com.example.starshipshop.domain.manufacturer.ManufacturerOutput;
import com.example.starshipshop.service.ManufacturerService;
import com.example.starshipshop.service.mapper.converter.HashToIdConverter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/manufacturers")
public class ManufacturerController {

	private final ManufacturerService manufacturerService;
	private final ManufacturerAssembler assembler;
	private final HashToIdConverter hashToIdConverter;

	@PreAuthorize("permitAll()")
	@GetMapping
	public CollectionModel<EntityModel<ManufacturerOutput>> getManufacturers() {
		List<EntityModel<ManufacturerOutput>> result = manufacturerService.getManufacturerOutput()
				.stream()
				.map(assembler::toModelWithSelfLink)
				.collect(Collectors.toList());
		return CollectionModel.of(result,
				linkTo(methodOn(ManufacturerController.class).getManufacturers()).withRel("manufacturers"));
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/{id}")
	public EntityModel<ManufacturerOutput> getManufacturerById(@PathVariable String id) {
		return assembler.toModel(manufacturerService.getManufacturerOuputById(hashToIdConverter.convert(id)));
	}

	@PreAuthorize("hasAuthority('starship:write')")
	@PostMapping
	public ResponseEntity<EntityModel<ManufacturerOutput>> createManufacturer(
			@RequestBody @Valid ManufacturerInput mi) {
		EntityModel<ManufacturerOutput> entityModel = this.assembler
				.toModel(this.manufacturerService.createManufacturer(mi));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
				.toUri())
				.body(entityModel);
	}

	@PreAuthorize("hasAuthority('starship:write')")
	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<ManufacturerOutput>> updateManufacturer(@PathVariable String id,
			@RequestBody @Valid ManufacturerInput mi) {
		EntityModel<ManufacturerOutput> response = assembler.toModel(
				this.manufacturerService.updateManufacturer(hashToIdConverter.convert(id), mi));
		return ResponseEntity.created(response.getRequiredLink(IanaLinkRelations.SELF)
				.toUri())
				.body(response);
	}

	@PreAuthorize("hasAuthority('starship:write')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteManufacturer(@PathVariable String id) {
		this.manufacturerService.deleteManufacturer(hashToIdConverter.convert(id));
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
