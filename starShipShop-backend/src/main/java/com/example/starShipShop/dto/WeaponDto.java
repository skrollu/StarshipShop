package com.example.starshipShop.dto;

import org.springframework.hateoas.server.core.Relation;

import com.example.starshipShop.mapper.converter.HashToIdConverter;
import com.example.starshipShop.mapper.converter.IdToHashConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Relation(collectionRelation = "weapons", itemRelation = "weapon")
public class WeaponDto {
	@JsonSerialize(converter = IdToHashConverter.class)
	@JsonDeserialize(converter = HashToIdConverter.class)
	private Long id;
	private String name;
	private ManufacturerDto manufacturer;
}
