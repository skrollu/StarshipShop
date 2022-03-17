package com.example.starshipShop.jpa;

import java.util.Objects;
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

@Entity
@Table(name = "manufacturer")
public class Manufacturer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_manufacturer", updatable = false)
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<HyperdriveSystem> getHyperdriveSystems() {
		return hyperdriveSystems;
	}

	public void setHyperdriveSystems(Set<HyperdriveSystem> hyperdriveSystems) {
		this.hyperdriveSystems = hyperdriveSystems;
	}

	public Set<Starship> getStarships() {
		return starships;
	}

	public void setStarships(Set<Starship> starships) {
		this.starships = starships;
	}

	public Set<Weapon> getWeapons() {
		return weapons;
	}

	public void setWeapons(Set<Weapon> weapons) {
		this.weapons = weapons;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Manufacturer other = (Manufacturer) obj;
		return id == other.id && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Manufacturer [id=" + id + ", name=" + name + "]";
	}

}