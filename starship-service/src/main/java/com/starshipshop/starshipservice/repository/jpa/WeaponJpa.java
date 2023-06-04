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
@Table(name = "weapon")
public class WeaponJpa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_weapon", updatable = false, columnDefinition = "BIGINT")
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@JsonManagedReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_manufacturer", nullable = true, foreignKey = @ForeignKey(name = "FK_manufacturer_weapon"))
	private ManufacturerJpa manufacturer;

	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@JsonBackReference
	@ManyToMany(mappedBy = "weapons", fetch = FetchType.LAZY)
	private List<StarshipJpa> starships;
}
