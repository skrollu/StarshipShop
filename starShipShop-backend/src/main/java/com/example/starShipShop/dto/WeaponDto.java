package com.example.starshipShop.dto;

import org.springframework.hateoas.server.core.Relation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Relation(collectionRelation = "weapons", itemRelation = "weapon")
public class WeaponDto {
	private long id;
	private String reference;
	private String name;
	private ManufacturerDto manufacturer;
}
