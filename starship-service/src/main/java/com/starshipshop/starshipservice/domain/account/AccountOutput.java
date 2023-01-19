package com.starshipshop.starshipservice.domain.account;

import java.util.List;

import com.starshipshop.starshipservice.domain.user.SimpleUserOutput;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AccountOutput {

    private String username;
    private List<SimpleUserOutput> users;

}