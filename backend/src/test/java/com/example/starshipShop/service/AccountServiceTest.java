package com.example.starshipshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.example.starshipshop.domain.AccountDto;
import com.example.starshipshop.domain.RegisterNewAccountRequestInput;
import com.example.starshipshop.domain.SimpleUserDto;
import com.example.starshipshop.exception.AccountUsernameAlreadyExistException;
import com.example.starshipshop.exception.NonMatchingPasswordException;
import com.example.starshipshop.repository.AccountRepository;
import com.example.starshipshop.repository.UserRepository;
import com.example.starshipshop.repository.jpa.user.Account;
import com.example.starshipshop.repository.model.SecurityUserDetails;
import com.example.starshipshop.service.mapper.AccountMapper;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private AccountRepository accountRepository;
    @MockBean
    private AccountMapper accountMapper;
    @MockBean
    private PasswordEncoder encoder;
    
    private AccountService accountService;
    private Account user;
    private Account admin;
    
    @BeforeEach
    public void setupAccountRepository() {
        accountService = new AccountService(accountRepository, userRepository, accountMapper, encoder);
        user = new Account(1L, "user@mail.com", "password", "USER", null);
        admin = new Account(1L, "admin@mail.com", "password", "ADMIN, USER", null);
    }
    
    @Test
    @Tag("Load user by username")
    public void loadUserByUsername_shouldReturnASecurityUserDetailsInstance() {
        final String username = "user@mail.com";
        when(accountRepository.findByUsername(username)).thenReturn(Optional.of(user));
        
        UserDetails userDetails = accountService.loadUserByUsername(username);
        
        verify(accountRepository, times(1)).findByUsername(username);
        assertEquals(SecurityUserDetails.class, userDetails.getClass());
    }
    
    @Test
    @Tag("Load user by username")
    public void loadUserByUsername_shouldReturnAUserDetailsWithTheGivenUsername() {
        final String username = "user@mail.com";
        when(accountRepository.findByUsername(username)).thenReturn(Optional.of(user));
        
        UserDetails userDetails = accountService.loadUserByUsername(username);
        
        verify(accountRepository, times(1)).findByUsername(username);
        assertEquals(username, userDetails.getUsername());
    }
    
    @Test
    @Tag("Load user by username")
    public void loadUserByUsername_shouldReturnAUserDetailsWithTheAssociatedPermissionAsAuthorities() {
        final String username = "admin@mail.com";
        when(accountRepository.findByUsername(username)).thenReturn(Optional.of(admin));
        
        UserDetails userDetails = accountService.loadUserByUsername(username);
        
        verify(accountRepository, times(1)).findByUsername(username);
        assertEquals(true,
        userDetails.getAuthorities().contains(new SimpleGrantedAuthority("starship:read"))
        );
        assertEquals(true,
        userDetails.getAuthorities().contains(new SimpleGrantedAuthority("starship:write"))
        );
        assertEquals(true,
        userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );
        assertEquals(true,userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
    
    @Test
    @Tag("Load user by username")
    public void loadUserByUsername_shouldReturnAUserDetailsWithTheGivenRoleAsAuthorities() {
        final String username = "admin";
        when(accountRepository.findByUsername(username)).thenReturn(Optional.of(admin));
        
        UserDetails userDetails = accountService.loadUserByUsername(username);
        
        verify(accountRepository, times(1)).findByUsername(username);
        assertEquals(4, userDetails.getAuthorities().size());
        assertEquals(true, userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
        assertEquals(
        true, userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
    
    @Test
    @Tag("Load user by username")
    public void loadUserByUsername_shouldReturnAUserDetailsWithTheGivenPassword() {
        final String username = "admin";
        when(accountRepository.findByUsername(username)).thenReturn(Optional.of(user));
        
        UserDetails userDetails = accountService.loadUserByUsername(username);
        
        verify(accountRepository, times(1)).findByUsername(username);
        assertEquals("password", userDetails.getPassword());
        assertNotEquals("wrongpassword", userDetails.getPassword());
    }
    
    @Test
    @Tag("Load user by username")
    public void loadUserByUsername_shouldThrowUsernameNotFoundException() {
        final String username = "wrongUsername";
        when(accountRepository.findByUsername(username)).thenReturn(Optional.empty());
        
        assertThrows(UsernameNotFoundException.class,
        () -> accountService.loadUserByUsername(username));
        
        verify(accountRepository, times(1)).findByUsername(username);
    }
    
    @Test
    @Tag("Get account")
    @DisplayName("Get account information without Authentication should throw error")
    void getAccount_withoutAuthentication_shouldThrowError() {
        assertThrows(IllegalArgumentException.class,
        () -> accountService.getAccount(null));
    }
    
    @Test
    @Tag("Get account")
    @DisplayName("Get account information without Principal should throw error")
    void getAccount_withoutPrincipal_shouldThrowError() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        assertThrows(IllegalArgumentException.class, () -> accountService.getAccount(auth));
    }
    
    @Test
    @Tag("Get account")
    @DisplayName("Get account information without Principal should throw error")
    void getAccountDto_withoutPrincipal_shouldThrowError() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        assertThrows(IllegalArgumentException.class, () -> accountService.getAccountDto(auth));
    }
    
    @Test
    @Tag("Email exists")
    @DisplayName("Email exists should works")
    void emailExists_shouldWorks() {
        final String email = "admin@mail.com";
        when(accountRepository.findByUsername(email)).thenReturn(Optional.of(admin));
        boolean emailExist = accountService.checkEmailExists(email);
        assertEquals(true, emailExist);
    }
    
    @Test
    @Tag("Email exists")
    @DisplayName("Email exists should not works")
    void emailExists_shouldNotWorks() {
        final String email = "admin@mail.com";
        when(accountRepository.findByUsername(email)).thenReturn(Optional.empty());
        boolean emailExist = accountService.checkEmailExists(email);
        assertEquals(false, emailExist);
    }
    
    @Test
    @Tag("Email exists")
    @DisplayName("Email exists when email hasn't the correct format should not works")
    void emailExists_whenEmailHasNotTheCorrectFormat_shouldNotWorks() {
        final String email = "wrongFormatEmail";
        
        boolean emailExist = accountService.checkEmailExists(email);
        assertEquals(false, emailExist);
    }
    
    @Test
    @Tag("RegisterNewAccount")
    @DisplayName("Register should works")    
    void register_shouldWorks() {
        RegisterNewAccountRequestInput input = new RegisterNewAccountRequestInput(user.getUsername(), user.getPassword(), user.getPassword());
        when(accountRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(accountRepository.save(any())).thenReturn(user);
        when(accountMapper.fromRegisterNewAccountRequestInput(input))
        .thenReturn(Account.builder().username(user.getUsername()).password(user.getPassword()).build());
        when(accountMapper.toAccountDto(any())).thenReturn(AccountDto
        .builder().username(user.getUsername()).build());
        
        AccountDto dto = accountService.registerNewAccount(input);
        
        assertNotNull(dto);          
        assertEquals(input.getUsername(), dto.getUsername());
    }
    
    @Test
    @Tag("RegisterNewAccount")
    @DisplayName("Register when passwords doesnot match should throw error")
    void register_whenPasswordAreNotMatching_shouldThrowError() {
        RegisterNewAccountRequestInput input = new RegisterNewAccountRequestInput(
        user.getUsername(), user.getPassword(), "notMatchingPassword");
        
        NonMatchingPasswordException e = assertThrows(
        NonMatchingPasswordException.class,
        () -> accountService.registerNewAccount(input));
        assertEquals(true, e.getMessage()
        .contains("The given passwords and matching password are different."));
    }
    
    @Test
    @Tag("RegisterNewAccount")
    @DisplayName("Register when the email exists should throw error")
    void register_whenEmailExists_shouldThrowError() {
        final String email = "admin@mail.com";
        when(accountRepository.findByUsername(email)).thenReturn(Optional.of(admin));
        
        AccountUsernameAlreadyExistException e = assertThrows(
        AccountUsernameAlreadyExistException.class, () -> accountService.registerNewAccount(new RegisterNewAccountRequestInput(email, "password", "password")));
        assertEquals(true, e.getMessage().contains(
        "There is an account with that email address: " + email));
    }
    
    @Test
    @Tag("RegisterNewAccount")
    @DisplayName("Register when mapper return null should throw error")
    void register_whenMapperReturnNull_shouldThrowError() {
        RegisterNewAccountRequestInput input = new RegisterNewAccountRequestInput(
        user.getUsername(), user.getPassword(), user.getPassword());
        when(accountRepository.findByUsername(user.getUsername()))
        .thenReturn(Optional.empty());
        when(accountMapper.fromRegisterNewAccountRequestInput(input))
        .thenReturn(null);
        NullPointerException e =
        assertThrows(NullPointerException.class,
        () -> accountService.registerNewAccount(input));
        assertEquals(true, e.getMessage()
        .contains("Error when mapping account."));
    }
    
    // Check Email exists
    @Test
    @Tag("Check email exists")
    @DisplayName("given an email when repository find it then return false")
    void givenAnEmail_whenRepositoryFindIt_thenReturnFalse() {
        String email = "test@mail.com";
        
        when(accountRepository.findByUsername(email)).thenReturn(Optional.of(admin));
        boolean result = accountService.checkEmailExists(email);
        assertEquals(true, result);
    }
    
    @Test
    @Tag("Check email exists")
    @DisplayName("given an email when repository doesn't find it then return true")
    void givenAnEmail_whenRepositoryDoesNotFindIt_thenReturnTrue() {
        String email = "test@mail.com";
        
        when(accountRepository.findByUsername(email)).thenReturn(Optional.empty());
        boolean result = accountService.checkEmailExists(email);
        assertEquals(false, result);
    }

}
