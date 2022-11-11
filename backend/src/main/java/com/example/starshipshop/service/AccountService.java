package com.example.starshipshop.service;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.starshipshop.config.security.SecurityUserRole;
import com.example.starshipshop.domain.AccountDto;
import com.example.starshipshop.domain.RegisterNewAccountRequestInput;
import com.example.starshipshop.exception.AccountUsernameAlreadyExistException;
import com.example.starshipshop.exception.NonMatchingPasswordException;
import com.example.starshipshop.repository.AccountRepository;
import com.example.starshipshop.repository.jpa.user.Account;
import com.example.starshipshop.repository.model.SecurityUserDetails;
import com.example.starshipshop.service.mapper.AccountMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RequiredArgsConstructor
@Service
@Slf4j
public class AccountService implements UserDetailsService {
    
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder encoder;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository
        .findByUsername(username)
        .map(SecurityUserDetails::new)
        .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    }
    
    public Optional<AccountDto> getAccount(Authentication authentication) throws IllegalArgumentException{
        Assert.notNull(authentication, "Authentication informations are null.");
        SecurityUserDetails userDetails = (SecurityUserDetails) authentication.getPrincipal();
        Assert.notNull(userDetails, "Principal informations are null.");
        return Optional.of(accountMapper.toAccountDto(userDetails.getAccount()));
    }
    
    public AccountDto registerNewAccount(
    RegisterNewAccountRequestInput rnari) throws AccountUsernameAlreadyExistException, NonMatchingPasswordException, IllegalArgumentException,
    NullPointerException {
        Assert.notNull(rnari, "Register new account resource is null");
        if(!rnari.getPassword().equals(rnari.getMatchingPassword())) {
            throw new NonMatchingPasswordException();
        }
        
        if (emailExists(rnari.getUsername())) {
            throw new AccountUsernameAlreadyExistException(
            "There is an account with that email address: " + rnari.getUsername());
        }
        
        Account toSave = accountMapper.fromRegisterNewAccountRequestInput(rnari);
        if(toSave == null){
            throw new NullPointerException("Error when mapping account.");
        }
        toSave.setPassword(encoder.encode(toSave.getPassword()));
        toSave.setRoles(SecurityUserRole.USER.name());
        Account result = accountRepository.save(toSave);
        return this.accountMapper.toAccountDto(result);
    }
    
    public boolean emailExists(String email) {
        Optional<Account> optAccount = accountRepository.findByUsername(email);
        return optAccount.isPresent() && optAccount.get() != null;
    }
}
