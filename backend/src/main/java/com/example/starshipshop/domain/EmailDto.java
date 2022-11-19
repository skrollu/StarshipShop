package com.example.starshipshop.domain;

import javax.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
// @AllArgsConstructor Not working for no reason...
public class EmailDto {
    @Email
    private String email;

    public EmailDto()  {}

    public EmailDto(String email) {
        this.email = email;
    }
}
