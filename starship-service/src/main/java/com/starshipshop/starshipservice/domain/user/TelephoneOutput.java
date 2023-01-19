package com.starshipshop.starshipservice.domain.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.starshipshop.starshipservice.service.mapper.serializer.IdCombinedSerializer.IdDeserializer;
import com.starshipshop.starshipservice.service.mapper.serializer.IdCombinedSerializer.IdSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TelephoneOutput {
    @JsonSerialize(using = IdSerializer.class)
    @JsonDeserialize(using = IdDeserializer.class)
    private Long id;
    private String telephoneNumber;
}
