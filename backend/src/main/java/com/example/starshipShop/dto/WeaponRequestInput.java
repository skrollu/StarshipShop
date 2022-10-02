package com.example.starshipShop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WeaponRequestInput {
	private String name;
	private ManufacturerDto manufacturer;
}
