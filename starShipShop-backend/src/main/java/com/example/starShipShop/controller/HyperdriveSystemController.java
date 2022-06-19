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

import com.example.starshipShop.assembler.HyperdriveSystemAssembler;
import com.example.starshipShop.jpa.HyperdriveSystem;
import com.example.starshipShop.requestDto.HyperdriveSystemRequestDTO;
import com.example.starshipShop.service.HyperdriveSystemService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/hyperdriveSystems")
public class HyperdriveSystemController {

	@Autowired
	private HyperdriveSystemService hyperdriveSystemService;

	@Autowired
	private HyperdriveSystemAssembler assembler;

	@GetMapping
	public CollectionModel<EntityModel<HyperdriveSystem>> getHyperdriveSystems() {
		List<EntityModel<HyperdriveSystem>> result = hyperdriveSystemService.getHyperdriveSystems()
																			.stream()
																			.map(assembler::toModelWithSelfLink)
																			.collect(Collectors.toList());
		return CollectionModel.of(result,
				linkTo(methodOn(HyperdriveSystemController.class).getHyperdriveSystems()).withSelfRel());
	}

	@GetMapping("/{id}")
	public EntityModel<HyperdriveSystem> getHyperdriveSystemById(@PathVariable Long id) {
		HyperdriveSystem hyperdriveSystem = hyperdriveSystemService	.getHyperdriveSystemById(id)
																	.get();
		EntityModel<HyperdriveSystem> result = this.assembler.toModel(hyperdriveSystem);
		return result;
	}

	@PostMapping
	public ResponseEntity<EntityModel<HyperdriveSystem>> createHyperdriveSystem(
			@RequestBody HyperdriveSystemRequestDTO hyperdriveSystem) {
		EntityModel<HyperdriveSystem> entityModel = assembler.toModel(
				this.hyperdriveSystemService.saveHyperdriveSystem(hyperdriveSystem));
		return ResponseEntity	.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
													.toUri()) //
								.body(entityModel);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<HyperdriveSystem>> updateHyperdriveSystem(@PathVariable Long id,
			@RequestBody HyperdriveSystemRequestDTO hyperdriveSystemRequestDTO) {
		EntityModel<HyperdriveSystem> entityModel = assembler.toModel(
				this.hyperdriveSystemService.updateHyperdriveSystem(id, hyperdriveSystemRequestDTO));

		return ResponseEntity //
								.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
													.toUri()) //
								.body(entityModel);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteHyperdriveSystem(@PathVariable Long id) {
		this.hyperdriveSystemService.deleteHyperdriveSystem(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}