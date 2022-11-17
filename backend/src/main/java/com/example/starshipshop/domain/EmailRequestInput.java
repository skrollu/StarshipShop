package com.example.starshipshop.domain;

import javax.validation.constraints.Email;
import lombok.Data;

@Data
public class EmailRequestInput {
    
    @Email
    private String email;
}
