package com.example.starshipShop.requestDto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StarshipRequestDTO {

	private String reference;
	private String name;
	private String engines;
	private double height;
	private double width;
	private double lenght;
	private double weight;
	private String description;
	private ManufacturerRequestDTO manufacturer;
	private HyperdriveSystemRequestDTO hyperdriveSystem;
	private List<WeaponRequestDTO> weapons;

}
