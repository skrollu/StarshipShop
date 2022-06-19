package com.example.starshipShop.dto;

import java.util.List;

import org.springframework.hateoas.server.core.Relation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Relation(collectionRelation = "starships", itemRelation = "starship")
public class StarshipDto {
	private long id;
	private String reference;
	private String name;
	private String engines;
	private double height;
	private double width;
	private double lenght;
	private double weight;
	private String description;
	private ManufacturerDto manufacturer;
	private HyperdriveSystemDto hyperdriveSystem;
	private List<WeaponDto> weapons;

}
