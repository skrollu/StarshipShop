package com.example.starshipShop.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TelephoneDto {
    private String telephoneNumber;
}
