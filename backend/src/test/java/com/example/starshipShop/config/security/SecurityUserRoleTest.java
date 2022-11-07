package com.example.starshipshop.config.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.example.starshipshop.config.security.SecurityUserRole;

public class SecurityUserRoleTest {
    
    @Test
    public void givenAUserRole_getGrantedAuthoritiesOfUserRole_shouldReturnAssociatedPermissions(){
        // GIVEN
        SecurityUserRole role = SecurityUserRole.USER;

        // WHEN
        Set<SimpleGrantedAuthority> grantedAuthorities = role.getGrantedAuthorities();

        // THEN
        assertEquals(2, grantedAuthorities.size());
        assertEquals(true, grantedAuthorities.contains(new SimpleGrantedAuthority("starship:read")));
        assertEquals(false, grantedAuthorities.contains(new SimpleGrantedAuthority("starship:write")));
        assertEquals(true, grantedAuthorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    public void givenAUserRole_getGrantedAuthoritiesOfAdminRole_shouldReturnAssociatedPermissions(){
        // GIVEN
        SecurityUserRole role = SecurityUserRole.ADMIN;

        // WHEN
        Set<SimpleGrantedAuthority> grantedAuthorities = role.getGrantedAuthorities();

        // THEN
        assertEquals(3, grantedAuthorities.size());
        assertEquals(true, grantedAuthorities.contains(new SimpleGrantedAuthority("starship:read")));
        assertEquals(true, grantedAuthorities.contains(new SimpleGrantedAuthority("starship:write")));
        assertEquals(true, grantedAuthorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
        assertEquals(false, grantedAuthorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertEquals(false, grantedAuthorities.contains(new SimpleGrantedAuthority("ROLE_SUPERUSER")));
    }
}
