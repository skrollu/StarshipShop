package com.example.starshipShop.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AddressDto {
    private String address;
    private Long zipCode;
    private String city;
    private String state;
    private String country;
    private String planet;
}
