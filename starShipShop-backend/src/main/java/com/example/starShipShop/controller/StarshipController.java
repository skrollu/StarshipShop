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

import com.example.starshipShop.assembler.StarshipAssembler;
import com.example.starshipShop.jpa.Starship;
import com.example.starshipShop.requestDto.StarshipRequestDTO;
import com.example.starshipShop.service.StarshipService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/starships")
public class StarshipController {

	@Autowired
	private StarshipService starshipService;

	@Autowired
	private StarshipAssembler assembler;

	@GetMapping
	public CollectionModel<EntityModel<Starship>> getStarships() {
		List<EntityModel<Starship>> result = starshipService.getStarships()
															.stream()
															.map(assembler::toModelWithSelfLink)
															.collect(Collectors.toList());
		return CollectionModel.of(result, linkTo(methodOn(StarshipController.class).getStarships()).withSelfRel());
	}

	@GetMapping("/{id}")
	public EntityModel<Starship> getStarshipById(@PathVariable Long id) {

		Starship starship = starshipService	.getStarshipById(id)
											.get();
		EntityModel<Starship> result = this.assembler.toModel(starship);
		return result;
	}

	@PostMapping
	public ResponseEntity<EntityModel<Starship>> createStarship(@RequestBody StarshipRequestDTO starship) {

		EntityModel<Starship> entityModel = assembler.toModel(this.starshipService.saveStarship(starship));
		return ResponseEntity	.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
													.toUri()) //
								.body(entityModel);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<Starship>> updateStarship(@PathVariable Long id,
			@RequestBody StarshipRequestDTO starshipRequestDTO) {

		EntityModel<Starship> entityModel = assembler.toModel(
				this.starshipService.updateStarship(id, starshipRequestDTO));

		return ResponseEntity //
								.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
													.toUri()) //
								.body(entityModel);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteStarship(@PathVariable Long id) {
		this.starshipService.deleteStarship(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
