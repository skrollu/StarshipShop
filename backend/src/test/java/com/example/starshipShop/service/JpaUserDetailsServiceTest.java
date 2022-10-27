package com.example.starshipShop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.example.starshipShop.repository.AccountRepository;
import com.example.starshipShop.repository.jpa.Account;
import com.example.starshipShop.repository.model.SecurityUserDetails;
import com.example.starshipShop.service.JpaUserDetailsService;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class JpaUserDetailsServiceTest {
    
    @MockBean
    private AccountRepository accountRepository;
    
    private JpaUserDetailsService jpaUserDetailsService;
    private Account user;
    private Account admin;
    
    @BeforeEach
    public void setupAccountRepository() {
        jpaUserDetailsService = new JpaUserDetailsService(accountRepository);
        user = new Account(1L, "user", "password", "USER");
        admin = new Account(1L, "admin", "password", "ADMIN, USER");
    }
    
    @Test
    public void loadUserByUsername_shouldReturnASecurityUserDetailsInstance() {
        final String username = "user";
        when(accountRepository.findByUsername(username)).thenReturn(Optional.of(user));
        
        UserDetails userDetails = jpaUserDetailsService.loadUserByUsername(username);
        
        verify(accountRepository, times(1)).findByUsername(username);
        assertEquals(userDetails.getClass(), SecurityUserDetails.class);
    }
    
    @Test
    public void loadUserByUsername_shouldReturnAUserDetailsWithTheGivenUsername() {
        final String username = "user";
        when(accountRepository.findByUsername(username)).thenReturn(Optional.of(user));
        
        UserDetails userDetails = jpaUserDetailsService.loadUserByUsername(username);
        
        verify(accountRepository, times(1)).findByUsername(username);
        assertEquals(userDetails.getUsername(), user.getUsername());
    }
    
    @Test
    public void loadUserByUsername_shouldReturnAUserDetailsWithTheAssociatedPermissionAsAuthorities() {
        final String username = "admin";
        when(accountRepository.findByUsername(username)).thenReturn(Optional.of(admin));
        
        UserDetails userDetails = jpaUserDetailsService.loadUserByUsername(username);
        
        verify(accountRepository, times(1)).findByUsername(username);
        assertEquals(
        userDetails.getAuthorities().contains(new SimpleGrantedAuthority("starship:read")),
        true);
        assertEquals(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("starship:write")),
        true);
        assertEquals(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")),
        true);
        assertEquals(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")),
        true);
    }
    
    @Test
    public void loadUserByUsername_shouldReturnAUserDetailsWithTheGivenRoleAsAuthorities() {
        final String username = "admin";
        when(accountRepository.findByUsername(username)).thenReturn(Optional.of(admin));
        
        UserDetails userDetails = jpaUserDetailsService.loadUserByUsername(username);
        
        verify(accountRepository, times(1)).findByUsername(username);
        assertEquals(
        userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")),
        true);
        assertEquals(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")),
        true);
    }
    
    @Test
    public void loadUserByUsername_shouldReturnAUserDetailsWithTheGivePassword() {
        final String username = "admin";
        when(accountRepository.findByUsername(username)).thenReturn(Optional.of(user));
        
        UserDetails userDetails = jpaUserDetailsService.loadUserByUsername(username);
        
        verify(accountRepository, times(1)).findByUsername(username);
        assertEquals(userDetails.getPassword(), "password");
    }
    
    @Test
    public void loadUserByUsername_shouldReturnAUserDetailsWithTheGiveACorrectPassword() {
        final String username = "admin";
        when(accountRepository.findByUsername(username)).thenReturn(Optional.of(admin));
        
        UserDetails userDetails = jpaUserDetailsService.loadUserByUsername(username);
        
        verify(accountRepository, times(1)).findByUsername(username);
        assertNotEquals(userDetails.getPassword(), "wrongpassword");
    }
    
    @Test
    public void loadUserByUsername_shouldThrowUsernameNotFoundException() {
        final String username = "wrongUsername";
        when(accountRepository.findByUsername(username)).thenReturn(Optional.empty());
        
        assertThrows(UsernameNotFoundException.class,() -> jpaUserDetailsService.loadUserByUsername(username));
        
        verify(accountRepository, times(1)).findByUsername(username);
    }
    
}
