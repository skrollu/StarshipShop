package com.example.starshipShop.jpa;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
@Entity
@Table(name = "manufacturer")
public class Manufacturer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_manufacturer", updatable = false, columnDefinition = "BIGINT")
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@JsonBackReference
	@OneToMany(mappedBy = "manufacturer", fetch = FetchType.LAZY)
	private Set<HyperdriveSystem> hyperdriveSystems;

	@JsonBackReference
	@OneToMany(mappedBy = "manufacturer", fetch = FetchType.LAZY)
	private Set<Starship> starships;

	@JsonBackReference
	@OneToMany(mappedBy = "manufacturer", fetch = FetchType.LAZY)
	private Set<Weapon> weapons;

	public Manufacturer() {
		super();

	}

	public Manufacturer(Long id, String name, Set<HyperdriveSystem> hyperdriveSystems, Set<Starship> starships,
			Set<Weapon> weapons) {
		super();
		this.id = id;
		this.name = name;
		this.hyperdriveSystems = hyperdriveSystems;
		this.starships = starships;
		this.weapons = weapons;
	}
}