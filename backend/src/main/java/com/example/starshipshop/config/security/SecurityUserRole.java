package com.example.starshipshop.config.security;

import static com.example.starshipshop.config.security.SecurityUserPermission.STARSHIP_READ;
import static com.example.starshipshop.config.security.SecurityUserPermission.STARSHIP_WRITE;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum SecurityUserRole {
    USER(Sets.newHashSet(STARSHIP_READ)),
    ADMIN(Sets.newHashSet(STARSHIP_READ, STARSHIP_WRITE));

    private final Set<SecurityUserPermission> permissions;

    SecurityUserRole(Set<SecurityUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<SecurityUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> result = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
                result.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return result;
    }
}