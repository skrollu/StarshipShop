package com.example.starshipShop.jpa;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "hyperdrive_system")
public class HyperdriveSystem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_hyperdrive_system", updatable = false)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@JsonManagedReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_manufacturer", nullable = true, foreignKey = @ForeignKey(name = "FK_manufacturer_hyperdriveSystem"))
	private Manufacturer manufacturer;

	@JsonBackReference
	@OneToMany(mappedBy = "hyperdriveSystem", fetch = FetchType.LAZY)
	private List<Starship> starships;
}