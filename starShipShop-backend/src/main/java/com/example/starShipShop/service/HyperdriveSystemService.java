package com.example.starshipShop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.starshipShop.jpa.HyperdriveSystem;
import com.example.starshipShop.jpa.Manufacturer;
import com.example.starshipShop.repository.HyperdriveSystemRepository;
import com.example.starshipShop.repository.ManufacturerRepository;

import lombok.Data;

@Data
@Service
public class HyperdriveSystemService {

	@Autowired
	private HyperdriveSystemRepository hyperdriveSystemRepository;

	public List<HyperdriveSystem> getHyperdriveSystems() {
		List<HyperdriveSystem> list = hyperdriveSystemRepository.findAll();
		return list;
	}

	public Optional<HyperdriveSystem> getHyperdriveSystem(final Long id) {
		return hyperdriveSystemRepository.findById(id);
	}

	public void deleteHyperdriveSystem(final Long id) {
		hyperdriveSystemRepository.deleteById(id);
	}

	public HyperdriveSystem saveHyperdriveSystem(HyperdriveSystem hyperdriveSystem) {
		HyperdriveSystem savedHyperdriveSystem = hyperdriveSystemRepository.save(hyperdriveSystem);
		return savedHyperdriveSystem;
	}
}
