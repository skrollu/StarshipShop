package com.example.starshipShop.service;

import static com.example.starshipShop.mapper.ManufacturerMapper.mapToEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.starshipShop.exception.ResourceNotFoundException;
import com.example.starshipShop.jpa.Manufacturer;
import com.example.starshipShop.repository.ManufacturerRepository;
import com.example.starshipShop.requestDto.ManufacturerRequestDTO;

import lombok.Data;

@Data
@Service
public class ManufacturerService {

	@Autowired
	private ManufacturerRepository manufacturerRepository;

	public List<Manufacturer> getManufacturers() {
		List<Manufacturer> list = manufacturerRepository.findAll();
		return list;
	}

	public Optional<Manufacturer> getManufacturerById(final Long id) {
		return manufacturerRepository.findById(id);
	}

	public Optional<Manufacturer> getManufacturerByName(final String name) {
		return manufacturerRepository.findByName(name);
	}

	public Manufacturer saveManufacturer(final ManufacturerRequestDTO manufacturerRequestDTO) {
		Assert.notNull(manufacturerRequestDTO.getName(), String.format("Name cannot be null."));
		Assert.hasText(manufacturerRequestDTO.getName(), String.format("Name cannot be empty."));
		Manufacturer manufacturer = mapToEntity(manufacturerRequestDTO);
		Manufacturer savedManufacturer = manufacturerRepository.save(manufacturer);
		return savedManufacturer;
	}

	public Manufacturer updateManufacturer(final Long id, ManufacturerRequestDTO manufacturerRequestDTO) {
		Assert.notNull(manufacturerRequestDTO.getName(), String.format("Name cannot be null."));
		Assert.hasText(manufacturerRequestDTO.getName(), String.format("Name cannot be empty."));
		Manufacturer manufacturerToUpdate = this.getManufacturerById(id)
												.orElseThrow(() -> new ResourceNotFoundException(
														"Manufacturer doesn't exist with this id " + id));
		manufacturerToUpdate = mapToEntity(manufacturerRequestDTO, manufacturerToUpdate);
		Manufacturer result = manufacturerRepository.save(manufacturerToUpdate);
		return result;
	}

	public void deleteManufacturer(final Long id) {
		Manufacturer manufacturerToDelete = this.getManufacturerById(id)
												.orElseThrow(() -> new ResourceNotFoundException(
														"Manufacturer doesn't exist with this id " + id));
		manufacturerRepository.delete(manufacturerToDelete);
	}
}
