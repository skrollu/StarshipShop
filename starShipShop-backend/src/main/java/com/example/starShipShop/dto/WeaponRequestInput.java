package com.example.starshipShop.dto;

import org.springframework.hateoas.server.core.Relation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WeaponRequestInput {
	private String name;
	private ManufacturerDto manufacturer;
}
