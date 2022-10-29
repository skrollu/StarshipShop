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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.starshipShop.controller.assembler.ManufacturerAssembler;
import com.example.starshipShop.domain.ManufacturerDto;
import com.example.starshipShop.domain.ManufacturerRequestInput;
import com.example.starshipShop.service.ManufacturerService;
import com.example.starshipShop.service.mapper.converter.HashToIdConverter;
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
	public CollectionModel<EntityModel<ManufacturerDto>> getManufacturers() {
		List<EntityModel<ManufacturerDto>> result = manufacturerService	.getManufacturersDto()
		.stream()
		.map(assembler::toModelWithSelfLink)
		.collect(Collectors.toList());
		return CollectionModel.of(result,
		linkTo(methodOn(ManufacturerController.class).getManufacturers()).withRel("manufacturers"));
	}
	
	@PreAuthorize("permitAll()")
	@GetMapping("/{id}")
	public EntityModel<ManufacturerDto> getManufacturerById(@PathVariable String id) {
		Optional<ManufacturerDto> manufacturer = manufacturerService.getManufacturerDtoById(hashToIdConverter.convert(id));
		if(manufacturer.isPresent()){
			return this.assembler.toModel(manufacturer.get());
		} else {
			throw new NullPointerException();
		}
	}
	
    @PreAuthorize("hasAuthority('starship:write')")
	@PostMapping
	public ResponseEntity<EntityModel<ManufacturerDto>> createManufacturer(@RequestBody ManufacturerRequestInput mri) {
		EntityModel<ManufacturerDto> entityModel = this.assembler.toModel(this.manufacturerService.createManufacturer(mri));
		return ResponseEntity	.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
		.toUri())
		.body(entityModel);
	}
	
    @PreAuthorize("hasAuthority('starship:write')")
	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<ManufacturerDto>> updateManufacturer(@PathVariable String id,
	@RequestBody ManufacturerRequestInput mri) {
		EntityModel<ManufacturerDto> response = assembler.toModel(
		this.manufacturerService.updateManufacturer(hashToIdConverter.convert(id), mri));
		return ResponseEntity	.created(response	.getRequiredLink(IanaLinkRelations.SELF)
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
