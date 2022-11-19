package com.example.starshipshop.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import com.example.starshipshop.domain.AccountDto;
import com.example.starshipshop.domain.AddressDto;
import com.example.starshipshop.domain.EmailDto;
import com.example.starshipshop.domain.RegisterNewAccountRequestInput;
import com.example.starshipshop.domain.SimpleUserDto;
import com.example.starshipshop.domain.TelephoneDto;
import com.example.starshipshop.repository.jpa.user.Account;
import com.example.starshipshop.repository.jpa.user.Address;
import com.example.starshipshop.repository.jpa.user.Email;
import com.example.starshipshop.repository.jpa.user.Telephone;
import com.example.starshipshop.repository.jpa.user.User;

public class AccountMapperTest {

    private AccountMapper accountMapper;
    private Email email;
    private Telephone telephone;
    private Address address;
    private User user;
    private Account account;

    @BeforeEach
    void setup() {
        email = new Email(1L, "mail@mail.fr", null);
        Set<Email> emails = new HashSet<>();
        emails.add(email);

        telephone = new Telephone(1L, "0123456789", null);
        Set<Telephone> telephones = new HashSet<>();
        telephones.add(telephone);

        address = new Address(1L, "23 Baker Court", 11224L, "Brooklyn", "NY", "USA", "Earth", null);
        Set<Address> addresses = new HashSet<>();
        addresses.add(address);

        user = new User(1L, "john", null, addresses, emails, telephones);
        List<User> users = new ArrayList<>();
        users.add(user);

        account = new Account(1L, "user", "password", "USER", users);

        accountMapper = new AccountMapperImpl();
    }

    @Test
    @Tag("Account mapping")
    @DisplayName("Given an account when mapped it should return a complete AccountDto")
    void givenAnAccount_whenMapped_shouldReturnACompleteAccountDto() {
        AccountDto dto = accountMapper.toAccountDto(account);

        assertEquals("user", dto.getUsername());
        assertEquals(true, dto.getUsers() != null);
        assertEquals(false, dto.getUsers().isEmpty());
    }

    @Test
    @Tag("Account mapping")
    @DisplayName("Given an account without users when mapped it should return a AccountDto")
    void givenAnAccountWithoutUsers_whenMapped_shouldReturnACompleteAccountDto() {
        account.setUsers(null);

        AccountDto dto = accountMapper.toAccountDto(account);

        assertEquals("user", dto.getUsername());
        assertEquals(true, dto.getUsers() == null);
    }

    @Test
    @Tag("Account mapping")
    @DisplayName("Given an account with empty users when mapped it should return a AccountDto")
    void givenAnAccountWithEmptyUsers_whenMapped_shouldReturnACompleteAccountDto() {
        account.setUsers(new ArrayList<>());

        AccountDto dto = accountMapper.toAccountDto(account);

        assertEquals("user", dto.getUsername());
        assertEquals(true, dto.getUsers() != null);
        assertEquals(true, dto.getUsers().isEmpty());
    }

    @Test
    @Tag("Account mapping")
    @DisplayName("Given a Register New Account Request Input when mapped it should return a Account")
    void givenARegisterNewAccountRequestInput_whenMapped_shouldReturnAnAccount() {
        RegisterNewAccountRequestInput input = RegisterNewAccountRequestInput.builder()
        .username("user@mail.com").password("password")
        .matchingPassword("password").build();

        Account account = accountMapper.fromRegisterNewAccountRequestInput(input);

        assertEquals("user@mail.com", account.getUsername());
        assertEquals("password", account.getPassword());
    }

    @Test
    @Tag("User mapping")
    @DisplayName("Given a User with when mapped it should return a SimpleUserDto")
    void givenAUser_whenMapped_shouldReturnACompleteSimpleUserDto() {

        SimpleUserDto dto = accountMapper.toSimpleUserDto(user);

        assertEquals("john", dto.getPseudo());
        assertEquals(true, dto.getAddresses() != null);
        assertEquals(false, dto.getAddresses().isEmpty());
        assertEquals(true, dto.getEmails() != null);
        assertEquals(false, dto.getEmails().isEmpty());
        assertEquals(true, dto.getTelephones() != null);
        assertEquals(false, dto.getTelephones().isEmpty());
    }

    @Test
    @Tag("User mapping")
    @DisplayName("Given an user without members when mapped it should return a SimpleUserDto")
    void givenAUserWithoutMembers_whenMapped_shouldReturnASimpleUserDto() {
        user.setAddresses(null);
        user.setEmails(null);
        user.setTelephones(null);

        SimpleUserDto dto = accountMapper.toSimpleUserDto(user);

        assertEquals("john", dto.getPseudo());
        assertEquals(true, dto.getAddresses() == null);
        assertEquals(true, dto.getEmails() == null);
        assertEquals(true, dto.getTelephones() == null);
    }

    @Test
    @Tag("User mapping")
    @DisplayName("Given an user without members when mapped it should return a SimpleUserDto")
    void givenAUserWithEmptyMembers_whenMapped_shouldReturnASimpleUserDto() {
        user.setAddresses(new HashSet());
        user.setEmails(new HashSet());
        user.setTelephones(new HashSet());

        SimpleUserDto dto = accountMapper.toSimpleUserDto(user);

        assertEquals("john", dto.getPseudo());
        assertEquals(true, dto.getAddresses() != null);
        assertEquals(true, dto.getAddresses().isEmpty());
        assertEquals(true, dto.getEmails() != null);
        assertEquals(true, dto.getEmails().isEmpty());
        assertEquals(true, dto.getTelephones() != null);
        assertEquals(true, dto.getTelephones().isEmpty());
    }

    @Test
    @Tag("Address mapping")
    @DisplayName("Given an address when mapped it should return a AddressDto")
    void givenAnAddressDto_whenMapped_shouldReturnAnAddress() {
        AddressDto dto = AddressDto.builder().address("10 Thompson Road").city("New York")
                .state("NY").zipCode(10028L).country("USA").planet("Earth").build();

        Address address = accountMapper.toAddress(dto);

        assertEquals("10 Thompson Road", address.getAddress());
        assertEquals("New York", address.getCity());
        assertEquals("NY", address.getState());
        assertEquals(10028L, address.getZipCode());
        assertEquals("USA", address.getCountry());
        assertEquals("Earth", address.getPlanet());
        assertEquals(null, address.getUser());

    }

    @Test
    @Tag("Email mapping")
    @DisplayName("Given an emailDto when mapped it should return an Email")
    void givenAnEmailDto_whenMapped_shouldReturnAnEmail() {
        EmailDto dto = EmailDto.builder().email("mail@mail.com").build();

        Email email = accountMapper.toEmail(dto);

        assertEquals("mail@mail.com", email.getEmail());
        assertEquals(null, email.getUser());
    }

    @Test
    @Tag("Address mapping")
    @DisplayName("Given a telephoneDto when mapped it should return a Telephone")
    void givenAnTelephoneDto_whenMapped_shouldReturnATelephone() {
        TelephoneDto dto = TelephoneDto.builder().telephoneNumber("0123456789").build();

        Telephone telephone = accountMapper.toTelephone(dto);

        assertEquals("0123456789", telephone.getTelephoneNumber());
        assertEquals(null, telephone.getUser());
    }
}
