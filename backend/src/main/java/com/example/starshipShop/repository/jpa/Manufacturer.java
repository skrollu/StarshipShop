package com.example.starshipshop.repository.jpa;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
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
	private List<HyperdriveSystem> hyperdriveSystems;

	@JsonBackReference
	@OneToMany(mappedBy = "manufacturer", fetch = FetchType.LAZY)
	private List<Starship> starships;

	@JsonBackReference
	@OneToMany(mappedBy = "manufacturer", fetch = FetchType.LAZY)
	private List<Weapon> weapons;
}