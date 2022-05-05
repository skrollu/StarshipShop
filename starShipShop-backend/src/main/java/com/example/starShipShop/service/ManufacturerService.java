package com.example.starshipShop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.starshipShop.jpa.Manufacturer;
import com.example.starshipShop.repository.ManufacturerRepository;

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

	public void deleteManufacturer(final Long id) {
		manufacturerRepository.deleteById(id);
	}

	public Manufacturer saveManufacturer(Manufacturer manufacturer) {
		Manufacturer savedManufacturer = manufacturerRepository.save(manufacturer);
		return savedManufacturer;
	}
}
