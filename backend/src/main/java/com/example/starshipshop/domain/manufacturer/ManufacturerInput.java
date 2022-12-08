package com.example.starshipshop.domain.manufacturer;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ManufacturerInput {

	@Nonnull
	@NotBlank
	private String name;
}
