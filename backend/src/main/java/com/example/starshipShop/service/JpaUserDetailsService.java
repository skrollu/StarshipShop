package com.example.starshipshop.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.starshipshop.repository.AccountRepository;
import com.example.starshipshop.repository.model.SecurityUserDetails;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class JpaUserDetailsService implements UserDetailsService {
    
    private final AccountRepository accountRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         return accountRepository
                .findByUsername(username)
                .map(SecurityUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    }
}
