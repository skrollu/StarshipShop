package com.starshipshop.starshipservice.controller;

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
import org.springframework.web.bind.annotation.*;

import com.starshipshop.starshipservice.controller.assembler.StarshipAssembler;
import com.starshipshop.starshipservice.domain.starship.StarshipInput;
import com.starshipshop.starshipservice.domain.starship.StarshipOutput;
import com.starshipshop.starshipservice.service.StarshipService;
import com.starshipshop.starshipservice.service.mapper.converter.HashToIdConverter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/starships")
public class StarshipController {

	private final StarshipService starshipService;
	private final StarshipAssembler assembler;
	private final HashToIdConverter hashToIdConverter;

	@PreAuthorize("permitAll()")
	@GetMapping
	public CollectionModel<EntityModel<StarshipOutput>> getStarships() {
		List<EntityModel<StarshipOutput>> result = starshipService.getStarshipsDto()
				.stream()
				.map(assembler::toModelWithSelfLink)
				.collect(Collectors.toList());
		return CollectionModel.of(result, linkTo(methodOn(StarshipController.class).getStarships()).withSelfRel());
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/{id}")
	public EntityModel<StarshipOutput> getStarshipById(@PathVariable String id) {
		return this.assembler.toModel(starshipService.getStarshipOutputById(hashToIdConverter.convert(id)));
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/in")
	public CollectionModel<EntityModel<StarshipOutput>> getStarshipByIds(@RequestParam List<String> ids) {
		List<Long> longIds = ids.stream().map(id -> hashToIdConverter.convert(id)).collect(Collectors.toList());
		List<EntityModel<StarshipOutput>> result = starshipService.getStarshipOutputByIds(longIds)
				.stream()
				.map(assembler::toModelWithSelfLink)
				.collect(Collectors.toList());
		return CollectionModel.of(result, linkTo(methodOn(StarshipController.class).getStarshipByIds(ids)).withSelfRel());
	}

	@PreAuthorize("hasAuthority('starship:write')")
	@PostMapping
	public ResponseEntity<EntityModel<StarshipOutput>> createStarship(@Valid @RequestBody StarshipInput si) {
		EntityModel<StarshipOutput> entityModel = assembler.toModel(this.starshipService.createStarship(si));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
				.toUri())
				.body(entityModel);
	}

	@PreAuthorize("hasAuthority('starship:write')")
	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<StarshipOutput>> updateStarship(@PathVariable String id,
			@Valid @RequestBody StarshipInput si) {

		EntityModel<StarshipOutput> entityModel = assembler.toModel(
				this.starshipService.updateStarship(hashToIdConverter.convert(id), si));

		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
				.toUri())
				.body(entityModel);
	}

	@PreAuthorize("hasAuthority('starship:write')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteStarship(@PathVariable String id) {
		this.starshipService.deleteStarship(hashToIdConverter.convert(id));
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
