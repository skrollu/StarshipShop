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

import com.example.starshipShop.assembler.ManufacturerAssembler;
import com.example.starshipShop.dto.ManufacturerDto;
import com.example.starshipShop.dto.ManufacturerRequestInput;
import com.example.starshipShop.mapper.converter.HashToIdConverter;
import com.example.starshipShop.service.ManufacturerService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/manufacturers")
public class ManufacturerController {

	@Autowired
	private ManufacturerService manufacturerService;
	@Autowired
	private ManufacturerAssembler assembler;

	@GetMapping
	public CollectionModel<EntityModel<ManufacturerDto>> getManufacturers() {
		List<EntityModel<ManufacturerDto>> result = manufacturerService	.getManufacturersDto()
																		.stream()
																		.map(assembler::toModelWithSelfLink)
																		.collect(Collectors.toList());
		return CollectionModel.of(result,
				linkTo(methodOn(ManufacturerController.class).getManufacturers()).withRel("manufacturers"));
	}

	@GetMapping("/{id}")
	public EntityModel<ManufacturerDto> getManufacturerById(@PathVariable String id) {
		ManufacturerDto manufacturer = manufacturerService	.getManufacturerDtoById(HashToIdConverter.convertToId(id))
															.get();
		return this.assembler.toModel(manufacturer);
	}

	@PostMapping
	public ResponseEntity<EntityModel<ManufacturerDto>> createManufacturer(@RequestBody ManufacturerRequestInput mri) {
		EntityModel<ManufacturerDto> entityModel = this.assembler.toModel(this.manufacturerService.createManufacturer(mri));
			return ResponseEntity	.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
													.toUri())
								.body(entityModel);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<ManufacturerDto>> updateManufacturer(@PathVariable String id,
			@RequestBody ManufacturerRequestInput mri) {
		EntityModel<ManufacturerDto> response = assembler.toModel(
				this.manufacturerService.updateManufacturer(HashToIdConverter.convertToId(id), mri));
		return ResponseEntity	.created(response	.getRequiredLink(IanaLinkRelations.SELF)
													.toUri())
								.body(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteManufacturer(@PathVariable String id) {
		this.manufacturerService.deleteManufacturer(HashToIdConverter.convertToId(id));
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
