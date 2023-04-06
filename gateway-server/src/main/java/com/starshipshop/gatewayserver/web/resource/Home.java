package com.starshipshop.gatewayserver.web.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class Home {

    @GetMapping("/")
    public String index(Principal principal) {
        return principal.toString();
    }
}