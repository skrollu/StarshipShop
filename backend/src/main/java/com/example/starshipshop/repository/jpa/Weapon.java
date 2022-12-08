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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "weapon")
public class Weapon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_weapon", updatable = false, columnDefinition = "BIGINT")
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@JsonManagedReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_manufacturer", nullable = true, foreignKey = @ForeignKey(name = "FK_manufacturer_weapon"))
	private Manufacturer manufacturer;

	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@JsonBackReference
	@ManyToMany(mappedBy = "weapons", fetch = FetchType.LAZY)
	private List<Starship> starships;
}
