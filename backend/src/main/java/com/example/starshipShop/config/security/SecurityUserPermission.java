package com.example.starshipShop.config.security;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum SecurityUserPermission {
    STARSHIP_READ("starship:read"),
    STARSHIP_WRITE("starship:write");

    private final String permission;

    SecurityUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}