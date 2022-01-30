package com.example.starShipShop.jpa;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "weapon")
public class Weapon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_weapon")
	private long id;

	@Column(name = "reference")
	private String reference;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_manufacturer", referencedColumnName = "id_manufacturer")
	private Manufacturer manufacturer;

	@ManyToMany(mappedBy = "rel_starship_weapon")
	private Set<Starship> starships;
}
