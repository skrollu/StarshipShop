package com.example.starshipShop.mapper;

import com.example.starshipShop.jpa.Manufacturer;
import com.example.starshipShop.requestDto.ManufacturerRequestDTO;

public class ManufacturerMapper {

	public static Manufacturer mapToEntity(ManufacturerRequestDTO manufacturerRequestDTO) {
		Manufacturer result = new Manufacturer();
		result.setName(manufacturerRequestDTO.getName());
		return result;
	}

	public static Manufacturer mapToEntity(ManufacturerRequestDTO manufacturerRequestDTO, Manufacturer result) {
		result.setName(manufacturerRequestDTO.getName());
		return result;
	}
	/*
	 * public static ManufacturerRequestDTO mapToDto(Manufacturer manufacturer) {
	 * ManufacturerRequestDTO result = new ManufacturerRequestDTO();
	 * result.setName(manufacturer.getName()); return result; }
	 */
}
