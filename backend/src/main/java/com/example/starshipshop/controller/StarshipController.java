package com.example.starshipshop.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

import com.example.starshipshop.controller.assembler.StarshipAssembler;
import com.example.starshipshop.domain.starship.StarshipOutput;
import com.example.starshipshop.domain.starship.StarshipInput;
import com.example.starshipshop.service.StarshipService;
import com.example.starshipshop.service.mapper.converter.HashToIdConverter;

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
