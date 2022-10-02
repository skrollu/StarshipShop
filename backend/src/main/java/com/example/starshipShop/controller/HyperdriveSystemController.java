package com.example.starshipShop.controller;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.starshipShop.assembler.HyperdriveSystemAssembler;
import com.example.starshipShop.dto.HyperdriveSystemDto;
import com.example.starshipShop.dto.HyperdriveSystemRequestInput;
import com.example.starshipShop.mapper.converter.HashToIdConverter;
import com.example.starshipShop.service.HyperdriveSystemService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/hyperdriveSystems")
public class HyperdriveSystemController {
	
	private final HyperdriveSystemService hyperdriveSystemService;
	
	private final HyperdriveSystemAssembler assembler;
	
	private final HashToIdConverter hashToIdConverter;
	
	@GetMapping
	public CollectionModel<EntityModel<HyperdriveSystemDto>> getHyperdriveSystems() {
		List<EntityModel<HyperdriveSystemDto>> result = hyperdriveSystemService	.getHyperdriveSystemsDto()
		.stream()
		.map(assembler::toModelWithSelfLink)
		.collect(Collectors.toList());
		return CollectionModel.of(result,
		linkTo(methodOn(HyperdriveSystemController.class).getHyperdriveSystems()).withSelfRel());
	}
	
	@GetMapping("/{id}")
	public EntityModel<HyperdriveSystemDto> getHyperdriveSystemById(@PathVariable String id) throws NullPointerException{
		Optional<HyperdriveSystemDto> optHS = hyperdriveSystemService	.getHyperdriveSystemDtoById(hashToIdConverter.convert(id));
		if(optHS.isPresent()){
			return this.assembler.toModel(optHS.get());
		} else {
			throw new NullPointerException();
		}
	}
	
	@PostMapping
	public ResponseEntity<EntityModel<HyperdriveSystemDto>> createHyperdriveSystem(
	@RequestBody HyperdriveSystemRequestInput hsri) {
		EntityModel<HyperdriveSystemDto> entityModel = assembler.toModel(
		this.hyperdriveSystemService.createHyperdriveSystem(hsri));
		return ResponseEntity	.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
		.toUri())
		.body(entityModel);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<HyperdriveSystemDto>> updateHyperdriveSystem(@PathVariable String id,
	@RequestBody HyperdriveSystemRequestInput hsri) {
		EntityModel<HyperdriveSystemDto> response = assembler.toModel(
		this.hyperdriveSystemService.updateHyperdriveSystem(hashToIdConverter.convert(id), hsri));
		return ResponseEntity	.created(response	.getRequiredLink(IanaLinkRelations.SELF)
		.toUri())
		.body(response);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteHyperdriveSystem(@PathVariable String id) {
		this.hyperdriveSystemService.deleteHyperdriveSystem(hashToIdConverter.convert(id));
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}