package com.example.starshipShop.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeaponRequestDTO {

	private String reference;
	private String name;
	private ManufacturerRequestDTO manufacturer;
}
