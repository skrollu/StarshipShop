package com.example.starshipShop.service;

import static com.example.starshipShop.mapper.HyperdriveSystemMapper.mapToEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.starshipShop.exception.ResourceNotFoundException;
import com.example.starshipShop.jpa.HyperdriveSystem;
import com.example.starshipShop.repository.HyperdriveSystemRepository;
import com.example.starshipShop.requestDto.HyperdriveSystemRequestDTO;

import lombok.Data;

@Data
@Service
public class HyperdriveSystemService {

	@Autowired
	private ManufacturerService manufacturerService;

	@Autowired
	private HyperdriveSystemRepository hyperdriveSystemRepository;

	public List<HyperdriveSystem> getHyperdriveSystems() {
		List<HyperdriveSystem> list = hyperdriveSystemRepository.findAll();
		return list;
	}

	public Optional<HyperdriveSystem> getHyperdriveSystemById(final Long id) {
		return hyperdriveSystemRepository.findById(id);
	}

	public Optional<HyperdriveSystem> getHyperdriveSystemByName(final String name) {
		return hyperdriveSystemRepository.findByName(name);
	}

	public HyperdriveSystem saveHyperdriveSystem(HyperdriveSystem hyperdriveSystem) {
		HyperdriveSystem savedHyperdriveSystem = hyperdriveSystemRepository.save(hyperdriveSystem);
		return savedHyperdriveSystem;
	}

	public HyperdriveSystem saveHyperdriveSystem(final HyperdriveSystemRequestDTO hyperdriveSystemRequestDTO) {
		// Check nullpointer of Manufacturer
		if (hyperdriveSystemRequestDTO.getManufacturer() == null) {
			throw new NullPointerException("The given Manufacturer is null");
		}
		if (hyperdriveSystemRequestDTO	.getManufacturer()
										.getName() == null
				|| hyperdriveSystemRequestDTO	.getManufacturer()
												.getName()
												.isEmpty()) {
			throw new NullPointerException("The given Manufacturer's name is null or empty");
		}
		HyperdriveSystem hyperdriveSystem = mapToEntity(hyperdriveSystemRequestDTO);
//		Manufacturer manufacturer = this.manufacturerService.getManufacturerByName(hyperdriveSystem	.getManufacturer()
//																									.getName())
//															.orElseThrow(() -> new ResourceNotFoundException(
//																	"The given Manufacturer doesn't exist with this name: "
//																			+ hyperdriveSystem	.getManufacturer()
//																								.getName()));
//		hyperdriveSystem.setManufacturer(manufacturer);
		return hyperdriveSystemRepository.save(hyperdriveSystem);
	}

	public HyperdriveSystem updateHyperdriveSystem(final Long id,
			HyperdriveSystemRequestDTO hyperdriveSystemRequestDTO) {
		// Check nullpointer of Manufacturer
		if (hyperdriveSystemRequestDTO.getManufacturer() == null) {
			throw new NullPointerException("The given Manufacturer is null");
		}
		if (hyperdriveSystemRequestDTO	.getManufacturer()
										.getName() == null
				|| hyperdriveSystemRequestDTO	.getManufacturer()
												.getName()
												.isEmpty()) {
			throw new NullPointerException("The given Manufacturer's name is null or empty");
		}
		HyperdriveSystem hyperdriveSystemFromDb = this	.getHyperdriveSystemById(id)
														.orElseThrow(() -> new ResourceNotFoundException(
																"HyperdriveSystem doesn't exist with this id " + id));
		HyperdriveSystem hyperdriveSystemToUpdate = mapToEntity(hyperdriveSystemRequestDTO, hyperdriveSystemFromDb);
//
//		Manufacturer manufacturer = this.manufacturerService.getManufacturerByName(
//				hyperdriveSystemRequestDTO	.getManufacturer()
//											.getName())
//															.orElseThrow(() -> new ResourceNotFoundException(
//																	"The given Manufacturer doesn't exist with this name: "
//																			+ hyperdriveSystemRequestDTO.getManufacturer()
//																										.getName()));
//		hyperdriveSystemToUpdate.setManufacturer(manufacturer);
		return this.saveHyperdriveSystem(hyperdriveSystemToUpdate);
	}

	public void deleteHyperdriveSystem(final Long id) {
		HyperdriveSystem hyperdriveSystemToDelete = this.getHyperdriveSystemById(id)
														.orElseThrow(() -> new ResourceNotFoundException(
																"HyperdriveSystem doesn't exist with this id " + id));
		hyperdriveSystemRepository.delete(hyperdriveSystemToDelete);
	}
}
