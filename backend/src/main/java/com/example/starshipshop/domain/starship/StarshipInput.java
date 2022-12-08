package com.example.starshipshop.domain.starship;

import java.util.List;

import javax.annotation.Nonnull;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.example.starshipshop.service.mapper.serializer.IdCombinedSerializer.IdDeserializer;
import com.example.starshipshop.service.mapper.serializer.IdCombinedSerializer.IdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StarshipInput {
	@Nonnull
	@NotBlank
	private String name;
	private String engines;
	private double height;
	private double width;
	private double lenght;
	private double weight;
	private String description;
	@JsonSerialize(using = IdSerializer.class)
	@JsonDeserialize(using = IdDeserializer.class)
	private Long manufacturerId;
	@JsonSerialize(using = IdSerializer.class)
	@JsonDeserialize(using = IdDeserializer.class)
	private Long hyperdriveSystemId;
	@Valid
	private List<StarshipWeaponInput> weapons;
}
