package com.example.starshipshop.domain.weapon;

import javax.annotation.Nonnull;
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
public class WeaponInput {
	@Nonnull
	@NotBlank
	private String name;
	@JsonSerialize(using = IdSerializer.class)
	@JsonDeserialize(using = IdDeserializer.class)
	private Long manufacturerId;

}
