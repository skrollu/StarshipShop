package com.example.starshipshop.repository.jpa;

import java.util.List;

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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "starship")
public class Starship {
	// Note: It's a good practice to put the owning side of a relationship in the
	// class/table where the foreign key will be held.

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_starship", updatable = false, columnDefinition = "BIGINT")
	private Long id;

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
	@JoinColumn(name = "id_manufacturer", nullable = true, foreignKey = @ForeignKey(name = "FK_manufacturer_starship"))
	private Manufacturer manufacturer;

	@JsonManagedReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_hyperdrive_system", nullable = true, foreignKey = @ForeignKey(name = "FK_hyperdriveSystem_starship"))
	private HyperdriveSystem hyperdriveSystem;

	@JsonManagedReference
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "starships_weapons", joinColumns = {
			@JoinColumn(name = "id_starship", nullable = false, foreignKey = @ForeignKey(name = "FK_starship")) 
		},
		inverseJoinColumns = {
			@JoinColumn(name = "id_weapon", nullable = false, foreignKey = @ForeignKey(name = "FK_weapon"))
		})
	private List<Weapon> weapons;
}