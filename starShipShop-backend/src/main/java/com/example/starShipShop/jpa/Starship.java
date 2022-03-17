package com.example.starshipShop.jpa;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "starship")
public class Starship {
	// Note: It's a good practice to put the owning side of a relationship in the
	// class/table where the foreign key will be held.

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_starship", updatable = false)
	private Long id;

	@Column(name = "reference", nullable = false)
	private String reference;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "engines")
	private String engines;

	@Column(name = "height")
	private double height;

	@Column(name = "width")
	private double width;

	@Column(name = "lenght")
	private double lenght;

	@Column(name = "weight")
	private double weight;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@JsonManagedReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_manufacturer", foreignKey = @ForeignKey(name = "FK_manufacturer_starship"))
	private Manufacturer manufacturer;

	@JsonManagedReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_hyperdrive_system", foreignKey = @ForeignKey(name = "FK_hyperdriveSystem_starship"))
	private HyperdriveSystem hyperdriveSystems;

	@JsonManagedReference
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "starships_weapons", joinColumns = {
			@JoinColumn(name = "id_starship", nullable = false, foreignKey = @ForeignKey(name = "FK_starship")) }, inverseJoinColumns = {
					@JoinColumn(name = "id_weapon", nullable = false, foreignKey = @ForeignKey(name = "FK_weapon")) })
	private List<Weapon> weapons;

	public Starship() {
		super();
	}

	public Starship(Long id, String reference, String name, String engines, double height, double width, double lenght,
			double weight, String description, Manufacturer manufacturer, HyperdriveSystem hyperdriveSystems,
			List<Weapon> weapons) {
		super();
		this.id = id;
		this.reference = reference;
		this.name = name;
		this.engines = engines;
		this.height = height;
		this.width = width;
		this.lenght = lenght;
		this.weight = weight;
		this.description = description;
		this.manufacturer = manufacturer;
		this.hyperdriveSystems = hyperdriveSystems;
		this.weapons = weapons;
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

	public String getEngines() {
		return engines;
	}

	public void setEngines(String engines) {
		this.engines = engines;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getLenght() {
		return lenght;
	}

	public void setLenght(double lenght) {
		this.lenght = lenght;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Weapon> getWeapons() {
		return weapons;
	}

	public void setWeapons(List<Weapon> weapons) {
		this.weapons = weapons;
	}

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public HyperdriveSystem getHyperdriveSystems() {
		return hyperdriveSystems;
	}

	public void setHyperdriveSystems(HyperdriveSystem hyperdriveSystems) {
		this.hyperdriveSystems = hyperdriveSystems;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, engines, height, id, lenght, name, reference, weight, width);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Starship other = (Starship) obj;
		return Objects.equals(description, other.description) && Objects.equals(engines, other.engines)
				&& Double.doubleToLongBits(height) == Double.doubleToLongBits(other.height)
				&& Objects.equals(id, other.id)
				&& Double.doubleToLongBits(lenght) == Double.doubleToLongBits(other.lenght)
				&& Objects.equals(name, other.name) && Objects.equals(reference, other.reference)
				&& Double.doubleToLongBits(weight) == Double.doubleToLongBits(other.weight)
				&& Double.doubleToLongBits(width) == Double.doubleToLongBits(other.width);
	}

	@Override
	public String toString() {
		return "Starship [id=" + id + ", reference=" + reference + ", name=" + name + ", engines=" + engines
				+ ", height=" + height + ", width=" + width + ", lenght=" + lenght + ", weight=" + weight
				+ ", description=" + description + "]";
	}

}