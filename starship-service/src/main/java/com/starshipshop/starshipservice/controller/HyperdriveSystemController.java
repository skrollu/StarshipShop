package com.starshipshop.starshipservice.controller;

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

import com.starshipshop.starshipservice.controller.assembler.HyperdriveSystemAssembler;
import com.starshipshop.starshipservice.domain.hyperdriveSystem.HyperdriveSystemInput;
import com.starshipshop.starshipservice.domain.hyperdriveSystem.HyperdriveSystemOutput;
import com.starshipshop.starshipservice.service.HyperdriveSystemService;
import com.starshipshop.starshipservice.service.mapper.converter.HashToIdConverter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/hyperdriveSystems")
public class HyperdriveSystemController {

	private final HyperdriveSystemService hyperdriveSystemService;
	private final HyperdriveSystemAssembler assembler;
	private final HashToIdConverter hashToIdConverter;

	@PreAuthorize("permitAll()")
	@GetMapping
	public CollectionModel<EntityModel<HyperdriveSystemOutput>> getHyperdriveSystems() {
		List<EntityModel<HyperdriveSystemOutput>> result = hyperdriveSystemService.getHyperdriveSystemsOutput()
				.stream()
				.map(assembler::toModelWithSelfLink)
				.collect(Collectors.toList());
		return CollectionModel.of(result,
				linkTo(methodOn(HyperdriveSystemController.class).getHyperdriveSystems()).withSelfRel());
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/{id}")
	public EntityModel<HyperdriveSystemOutput> getHyperdriveSystemById(@PathVariable String id)
			throws NullPointerException {
		return this.assembler.toModel(hyperdriveSystemService
				.getHyperdriveSystemOutputById(hashToIdConverter.convert(id)));
	}

	@PreAuthorize("hasAuthority('starship:write')")
	@PostMapping
	public ResponseEntity<EntityModel<HyperdriveSystemOutput>> createHyperdriveSystem(
			@RequestBody HyperdriveSystemInput hri) {
		EntityModel<HyperdriveSystemOutput> entityModel = assembler.toModel(
				this.hyperdriveSystemService.createHyperdriveSystem(hri));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
				.toUri())
				.body(entityModel);
	}

	@PreAuthorize("hasAuthority('starship:write')")
	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<HyperdriveSystemOutput>> updateHyperdriveSystem(@PathVariable String id,
			@RequestBody HyperdriveSystemInput hri) {
		EntityModel<HyperdriveSystemOutput> response = assembler.toModel(
				this.hyperdriveSystemService.updateHyperdriveSystem(hashToIdConverter.convert(id), hri));
		return ResponseEntity.created(response.getRequiredLink(IanaLinkRelations.SELF)
				.toUri())
				.body(response);
	}

	@PreAuthorize("hasAuthority('starship:write')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteHyperdriveSystem(@PathVariable String id) {
		this.hyperdriveSystemService.deleteHyperdriveSystem(hashToIdConverter.convert(id));
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}