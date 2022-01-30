package com.example.starshipShop.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "hyperdrive_system")
public class HyperdriveSystem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_hyperdrive_system")
	private long id;

	@Column(name = "reference")
	private String reference;

	@Column(name = "name")
	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_manufacturer", referencedColumnName = "id_manufacturer")
	private Manufacturer manufacturer;
}
