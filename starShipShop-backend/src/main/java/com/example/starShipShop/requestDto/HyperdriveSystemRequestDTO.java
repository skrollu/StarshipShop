package com.example.starshipShop.requestDto;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HyperdriveSystemRequestDTO {
	private String reference;
	private String name;
	@Nullable
	private ManufacturerRequestDTO manufacturer;
}
