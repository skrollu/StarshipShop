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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.starshipshop.common.exception.AccountUsernameAlreadyExistException;
import com.example.starshipshop.common.exception.NonMatchingPasswordException;
import com.example.starshipshop.domain.account.AccountOutput;
import com.example.starshipshop.domain.account.CreateAccountInput;
import com.example.starshipshop.repository.AccountRepository;
import com.example.starshipshop.repository.AddressRepository;
import com.example.starshipshop.repository.EmailRepository;
import com.example.starshipshop.repository.TelephoneRepository;
import com.example.starshipshop.repository.UserRepository;
import com.example.starshipshop.repository.jpa.user.Account;
import com.example.starshipshop.repository.jpa.user.Address;
import com.example.starshipshop.repository.jpa.user.Email;
import com.example.starshipshop.repository.jpa.user.Telephone;
import com.example.starshipshop.repository.jpa.user.User;
import com.example.starshipshop.repository.model.SecurityUserDetails;
import com.example.starshipshop.service.mapper.AccountMapper;
import com.example.starshipshop.service.mapper.converter.IdToHashConverter;

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
    @MockBean
    private AddressRepository addressRepository;
    @MockBean
    private EmailRepository emailRepository;
    @MockBean
    private TelephoneRepository telephoneRepository;
    @MockBean
    private IdToHashConverter idToHashConverter;

    private AccountService accountService;
    private Account user;
    private Account admin;

    @BeforeEach
    public void setupAccountRepository() {
        accountService = new AccountService(accountRepository, userRepository, addressRepository,
                emailRepository,
                telephoneRepository, accountMapper, encoder, idToHashConverter);
        user = new Account(1L, "user@mail.com", "password", "USER", null);
        admin = new Account(1L, "admin@mail.com", "password", "ADMIN, USER", null);

        User u = User.builder().account(user).pseudo("john").emails(null).addresses(null)
                .telephones(null).build();

        Telephone t = Telephone.builder().user(u).telephoneNumber("0123456789").build();
        Email e = Email.builder().user(u).email("mail@mail.com").build();
        Address a = Address.builder()
                .user(u)
                .address("8 Rue du bourg")
                .city("Paris")
                .zipCode(75001L)
                .state("Ile de France")
                .country("France")
                .planet("Earth")
                .build();
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
                userDetails.getAuthorities().contains(new SimpleGrantedAuthority("starship:read")));
        assertEquals(true,
                userDetails.getAuthorities().contains(new SimpleGrantedAuthority("starship:write")));
        assertEquals(true,
                userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
        assertEquals(true, userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
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
                true, userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
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
    void getAccountOutput_withoutPrincipal_shouldThrowError() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        assertThrows(IllegalArgumentException.class, () -> accountService.getAccountOutput(auth));
    }

    @Test
    @Tag("Email exists")
    @DisplayName("Email exists should works")
    void emailExists_shouldWorks() {
        final String email = "admin@mail.com";
        when(accountRepository.findByUsername(email)).thenReturn(Optional.of(admin));
        boolean emailExist = accountService.checkUsernameExists(email);
        assertEquals(true, emailExist);
    }

    @Test
    @Tag("Email exists")
    @DisplayName("Email exists should not works")
    void emailExists_shouldNotWorks() {
        final String email = "admin@mail.com";
        when(accountRepository.findByUsername(email)).thenReturn(Optional.empty());
        boolean emailExist = accountService.checkUsernameExists(email);
        assertEquals(false, emailExist);
    }

    @Test
    @Tag("Email exists")
    @DisplayName("Email exists when email hasn't the correct format should not works")
    void emailExists_whenEmailHasNotTheCorrectFormat_shouldNotWorks() {
        final String email = "wrongFormatEmail";

        boolean emailExist = accountService.checkUsernameExists(email);
        assertEquals(false, emailExist);
    }

    @Test
    @Tag("RegisterNewAccount")
    @DisplayName("Register should works")
    void register_shouldWorks() {
        CreateAccountInput input = new CreateAccountInput(user.getUsername(), user.getPassword(),
                user.getPassword());
        when(accountRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(accountRepository.save(any())).thenReturn(user);
        when(accountMapper.fromCreateAccountInput(input))
                .thenReturn(Account.builder().username(user.getUsername()).password(user.getPassword())
                        .build());
        when(accountMapper.toAccountOutput(any())).thenReturn(AccountOutput
                .builder().username(user.getUsername()).build());

        AccountOutput dto = accountService.createAccount(input);

        assertNotNull(dto);
        assertEquals(input.getUsername(), dto.getUsername());
    }

    @Test
    @Tag("RegisterNewAccount")
    @DisplayName("Register when passwords doesnot match should throw error")
    void register_whenPasswordAreNotMatching_shouldThrowError() {
        CreateAccountInput input = new CreateAccountInput(
                user.getUsername(), user.getPassword(), "notMatchingPassword");

        NonMatchingPasswordException e = assertThrows(
                NonMatchingPasswordException.class,
                () -> accountService.createAccount(input));
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
                AccountUsernameAlreadyExistException.class,
                () -> accountService.createAccount(
                        new CreateAccountInput(email, "password", "password")));
        assertEquals(true, e.getMessage().contains(
                "There is an account with that email address: " + email));
    }

    @Test
    @Tag("RegisterNewAccount")
    @DisplayName("Register when mapper return null should throw error")
    void register_whenMapperReturnNull_shouldThrowError() {
        CreateAccountInput input = new CreateAccountInput(
                user.getUsername(), user.getPassword(), user.getPassword());
        when(accountRepository.findByUsername(user.getUsername()))
                .thenReturn(Optional.empty());
        when(accountMapper.fromCreateAccountInput(input))
                .thenReturn(null);
        NullPointerException e = assertThrows(NullPointerException.class,
                () -> accountService.createAccount(input));
        assertEquals(true, e.getMessage()
                .contains("Error when mapping account."));
    }

    // Check username exists
    @Test
    @Tag("Check username exists")
    @DisplayName("given an email when repository find it then return false")
    void givenAnEmail_whenRepositoryFindIt_thenReturnFalse() {
        String email = "test@mail.com";

        when(accountRepository.findByUsername(email)).thenReturn(Optional.of(admin));
        boolean result = accountService.checkUsernameExists(email);
        assertEquals(true, result);
    }

    @Test
    @Tag("Check username exists")
    @DisplayName("given an email when repository doesn't find it then return true")
    void givenAnEmail_whenRepositoryDoesNotFindIt_thenReturnTrue() {
        String email = "test@mail.com";

        when(accountRepository.findByUsername(email)).thenReturn(Optional.empty());
        boolean result = accountService.checkUsernameExists(email);
        assertEquals(false, result);
    }
}
