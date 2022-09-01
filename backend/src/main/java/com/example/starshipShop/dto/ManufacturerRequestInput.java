package com.example.starshipShop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@NonNull
public class ManufacturerRequestInput {

	private String name;
}
