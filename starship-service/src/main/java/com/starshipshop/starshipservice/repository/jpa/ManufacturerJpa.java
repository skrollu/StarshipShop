package com.starshipshop.starshipservice.repository.jpa;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "manufacturer")
public class ManufacturerJpa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_manufacturer", updatable = false, columnDefinition = "BIGINT")
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@JsonBackReference
	@OneToMany(mappedBy = "manufacturer", fetch = FetchType.LAZY)
	private List<HyperdriveSystemJpa> hyperdriveSystems;

	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@JsonBackReference
	@OneToMany(mappedBy = "manufacturer", fetch = FetchType.LAZY)
	private List<StarshipJpa> starships;

	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@JsonBackReference
	@OneToMany(mappedBy = "manufacturer", fetch = FetchType.LAZY)
	private List<WeaponJpa> weapons;
}