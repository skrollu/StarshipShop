package com.example.starshipShop.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.starshipShop.controller.ManufacturerController;
import com.example.starshipShop.dto.ManufacturerDto;
import com.example.starshipShop.mapper.converter.IdToHashConverter;

@Component
public class ManufacturerAssembler
		implements RepresentationModelAssembler<ManufacturerDto, EntityModel<ManufacturerDto>> {

	@Override
	public EntityModel<ManufacturerDto> toModel(ManufacturerDto manufacturer) {
		return EntityModel.of(manufacturer, linkTo(methodOn(ManufacturerController.class).getManufacturerById(
				IdToHashConverter.convertToId(manufacturer.getId()))).withSelfRel(),
//				linkTo(methodOn(ManufacturerController.class).createManufacturer(manufacturer)).withRel("create"),
//				linkTo(methodOn(ManufacturerController.class).updateManufacturer(manufacturer.getId(),
//						manufacturer)).withRel("update"),
//				linkTo(methodOn(ManufacturerController.class).deleteManufacturer(manufacturer.getId())).withRel(
//						"delete"),
				linkTo(methodOn(ManufacturerController.class).getManufacturers()).withRel("manufacturers"));
	}

	public EntityModel<ManufacturerDto> toModelWithSelfLink(ManufacturerDto manufacturer) {
		return EntityModel.of(manufacturer, linkTo(methodOn(ManufacturerController.class).getManufacturerById(
				IdToHashConverter.convertToId(manufacturer.getId()))).withSelfRel());
	}
}