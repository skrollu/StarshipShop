package com.example.starshipShop.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.starshipShop.controller.ManufacturerController;
import com.example.starshipShop.jpa.Manufacturer;

@Component
public class ManufacturerAssembler implements RepresentationModelAssembler<Manufacturer, EntityModel<Manufacturer>> {

	@Override
	public EntityModel<Manufacturer> toModel(Manufacturer manufacturer) {
		return EntityModel.of(manufacturer,
				linkTo(methodOn(ManufacturerController.class).getManufacturerById(manufacturer.getId())).withSelfRel(),
				linkTo(methodOn(ManufacturerController.class).getManufacturerByName(
						manufacturer.getName())).withSelfRel(),
				linkTo(methodOn(ManufacturerController.class).getManufacturers()).withRel("manufacturers"));
	}

	public EntityModel<Manufacturer> toModelWithSelfLink(Manufacturer manufacturer) {
		return EntityModel.of(manufacturer,
				linkTo(methodOn(ManufacturerController.class).getManufacturerById(manufacturer.getId())).withSelfRel());

	}
}