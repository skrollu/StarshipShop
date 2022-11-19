package com.example.starshipshop.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
// @AllArgsConstructor Not working for no reason...
public class TelephoneDto {
    private String telephoneNumber;

    public TelephoneDto()  {}

    public TelephoneDto(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }
}
