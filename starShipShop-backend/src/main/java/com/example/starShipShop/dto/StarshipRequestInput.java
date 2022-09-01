package com.example.starshipShop.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StarshipRequestInput {
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
