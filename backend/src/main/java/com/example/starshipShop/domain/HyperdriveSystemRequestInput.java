package com.example.starshipshop.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HyperdriveSystemRequestInput {

	private String name;
	private ManufacturerDto manufacturer;
}
