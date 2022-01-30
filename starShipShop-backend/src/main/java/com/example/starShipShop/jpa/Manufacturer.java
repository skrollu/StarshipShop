package com.example.starShipShop.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "manufacturer")
public class Manufacturer {

	// Note: It's a good practice to put the owning side of a relationship in the
	// class/table where the foreign key will be held.Note: It's a good practice to
	// put the owning side of a relationship in the class/table where the foreign
	// key will be held.

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_manufacturer")
	private long id;

	@Column(name = "name")
	private String name;
}