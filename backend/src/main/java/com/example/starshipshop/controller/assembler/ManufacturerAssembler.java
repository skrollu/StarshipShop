package com.example.starshipshop.controller.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.starshipshop.controller.ManufacturerController;
import com.example.starshipshop.domain.manufacturer.ManufacturerOutput;
import com.example.starshipshop.service.mapper.converter.IdToHashConverter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ManufacturerAssembler
		implements RepresentationModelAssembler<ManufacturerOutput, EntityModel<ManufacturerOutput>> {

	private final IdToHashConverter idToHashConverter;

	@Override
	public EntityModel<ManufacturerOutput> toModel(ManufacturerOutput manufacturer) {
		return EntityModel.of(manufacturer, linkTo(methodOn(ManufacturerController.class).getManufacturerById(
				idToHashConverter.convert(manufacturer.getId()))).withSelfRel(),
				// linkTo(methodOn(ManufacturerController.class).createManufacturer(manufacturer)).withRel("create"),
				// linkTo(methodOn(ManufacturerController.class).updateManufacturer(manufacturer.getId(),
				// manufacturer)).withRel("update"),
				// linkTo(methodOn(ManufacturerController.class).deleteManufacturer(manufacturer.getId())).withRel(
				// "delete"),
				linkTo(methodOn(ManufacturerController.class).getManufacturers()).withRel("manufacturers"));
	}

	public EntityModel<ManufacturerOutput> toModelWithSelfLink(ManufacturerOutput manufacturer) {
		return EntityModel.of(manufacturer, linkTo(methodOn(ManufacturerController.class).getManufacturerById(
				idToHashConverter.convert(manufacturer.getId()))).withSelfRel());
	}
}