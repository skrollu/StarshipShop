package com.starshipshop.starshipservice.repository.jpa;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "hyperdrive_system")
public class HyperdriveSystemJpa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_hyperdrive_system", updatable = false)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@JsonManagedReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_manufacturer", nullable = true, foreignKey = @ForeignKey(name = "FK_manufacturer_hyperdriveSystem"))
	private ManufacturerJpa manufacturer;

	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@JsonBackReference
	@OneToMany(mappedBy = "hyperdriveSystem", fetch = FetchType.LAZY)
	private List<StarshipJpa> starships;
}