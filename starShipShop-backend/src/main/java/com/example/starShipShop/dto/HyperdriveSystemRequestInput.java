package com.example.starshipShop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HyperdriveSystemRequestInput {

	private String name;
	private ManufacturerDto manufacturer;
}
