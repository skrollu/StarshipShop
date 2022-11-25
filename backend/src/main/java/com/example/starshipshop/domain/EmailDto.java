package com.example.starshipshop.domain;

import javax.validation.constraints.Email;
import com.example.starshipshop.service.mapper.serializer.IdCombinedSerializer.IdDeserializer;
import com.example.starshipshop.service.mapper.serializer.IdCombinedSerializer.IdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
// @AllArgsConstructor Not working for no reason...
public class EmailDto {
    @JsonSerialize(using = IdSerializer.class)
    @JsonDeserialize(using = IdDeserializer.class)
    private Long id;
    @Email
    private String email;

    public EmailDto()  {}

    public EmailDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
