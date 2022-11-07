package com.example.starshipshop.repository.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.starshipshop.config.security.SecurityUserRole;
import com.example.starshipshop.repository.jpa.user.Account;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
public class SecurityUserDetails implements UserDetails {

    private Account account;

    public SecurityUserDetails(Account account) {
        this.account = account;
    }

    @Override
    public String getUsername() {
        return account.getUsername();
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String[] roles = account.getRoles().split(",");
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (String r : roles) {
            r = r.trim();
            SecurityUserRole securityUserRole = Enum.valueOf(SecurityUserRole.class, r);
            if (securityUserRole != null) {
                authorities.addAll(securityUserRole.getGrantedAuthorities());
            }
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
