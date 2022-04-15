package com.example.starshipShop.jpa;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "weapon")
public class Weapon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_weapon", updatable = false, columnDefinition = "BIGINT")
	private Long id;

	@Column(name = "reference", nullable = false)
	private String reference;

	@Column(name = "name", nullable = false)
	private String name;

	@JsonManagedReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_manufacturer", nullable = false, foreignKey = @ForeignKey(name = "FK_manufacturer_weapon"))
	private Manufacturer manufacturer;

	@JsonBackReference
	@ManyToMany(mappedBy = "weapons", fetch = FetchType.LAZY)
	private Set<Starship> starships;

	public Weapon() {
		super();
	}

	public Weapon(Long id, String reference, String name, Manufacturer manufacturer, Set<Starship> starships) {
		super();
		this.id = id;
		this.reference = reference;
		this.name = name;
		this.manufacturer = manufacturer;
		this.starships = starships;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Set<Starship> getStarships() {
		return starships;
	}

	public void setStarships(Set<Starship> starships) {
		this.starships = starships;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, reference);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Weapon other = (Weapon) obj;
		return id == other.id && Objects.equals(name, other.name) && Objects.equals(reference, other.reference);
	}

	@Override
	public String toString() {
		return "Weapon [id=" + id + ", reference=" + reference + ", name=" + name + "]";
	}

}
