package com.example.starshipshop.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AccountDto {

    private String username;
    private List<SimpleUserDto> users;

}
