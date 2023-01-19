package com.starshipshop.starshipservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    
    @PreAuthorize("permitAll()")
    @GetMapping("/")
    public String home( ) {
        return "Welcome, home !";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/user")
    public String user() {
        return "Welcome, user !";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String admin() {
        return "Welcome, admin !";
    }

    @PreAuthorize("hasAuthority('starship:read')")
    @GetMapping("/read")
    public String read() {
        return "Welcome, reader !";
    }

    @PreAuthorize("hasAuthority('starship:write')")
    @GetMapping("/write")
    public String write() {
        return "Welcome, writer !";
    }
}
