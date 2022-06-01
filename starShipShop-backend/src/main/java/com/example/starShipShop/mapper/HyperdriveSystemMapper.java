package com.example.starshipShop.mapper;

import com.example.starshipShop.jpa.HyperdriveSystem;
import com.example.starshipShop.requestDto.HyperdriveSystemRequestDTO;

public class HyperdriveSystemMapper {

	public static HyperdriveSystem mapToEntity(HyperdriveSystemRequestDTO hyperdriveSystemRequestDTO) {
		HyperdriveSystem result = new HyperdriveSystem();
		result.setReference(hyperdriveSystemRequestDTO.getReference());
		result.setName(hyperdriveSystemRequestDTO.getName());
		if (hyperdriveSystemRequestDTO.getManufacturer() != null) {
			result.setManufacturer(ManufacturerMapper.mapToEntity(hyperdriveSystemRequestDTO.getManufacturer()));
		}
		return result;
	}

	public static HyperdriveSystem mapToEntity(HyperdriveSystemRequestDTO hyperdriveSystemRequestDTO,
			HyperdriveSystem result) {
		result.setReference(hyperdriveSystemRequestDTO.getReference());
		result.setName(hyperdriveSystemRequestDTO.getName());
		if (hyperdriveSystemRequestDTO.getManufacturer() != null) {
			result.setManufacturer(ManufacturerMapper.mapToEntity(hyperdriveSystemRequestDTO.getManufacturer()));
		}
		return result;
	}

	public static HyperdriveSystemRequestDTO mapToDto(HyperdriveSystem hyperdriveSystem) {
		HyperdriveSystemRequestDTO result = new HyperdriveSystemRequestDTO();
		result.setReference(hyperdriveSystem.getReference());
		result.setName(hyperdriveSystem.getName());
		result.setManufacturer(ManufacturerMapper.mapToDto(hyperdriveSystem.getManufacturer()));
		return result;
	}

}
