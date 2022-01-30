package com.example.starshipShop.jpa;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "starship")
public class Starship {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_hyperdrive_system")
	private long id;

	@Column(name = "reference")
	private String reference;

	@Column(name = "name")
	private String name;

	@Column(name = "engines")
	private String engines;

	@Column(name = "height")
	private float height;

	@Column(name = "width")
	private float width;

	@Column(name = "lenght")
	private float lenght;

	@Column(name = "weight")
	private float weight;

	@Column(name = "description")
	private String description;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_hyperdrive_system", referencedColumnName = "id_hyperdrive_system")
	private HyperdriveSystem hyperdriveSystem;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_manufacturer", referencedColumnName = "id_manufacturer")
	private Manufacturer manufacturer;

	@ManyToMany
	@JoinTable(name = "rel_starship_weapon", joinColumns = @JoinColumn(name = "id_starship"))
	private List<Weapon> weapons;
}
