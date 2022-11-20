package com.example.starshipshop.domain;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SimpleUserDto {

    private String pseudo;
    private Set<AddressDto> addresses;
    private Set<EmailDto> emails;
    private Set<TelephoneDto> telephones;
}
