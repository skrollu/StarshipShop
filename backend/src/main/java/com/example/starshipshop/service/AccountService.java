package com.example.starshipshop.service;

import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.example.starshipshop.config.security.SecurityUserRole;
import com.example.starshipshop.domain.AccountDto;
import com.example.starshipshop.domain.RegisterNewAccountRequestInput;
import com.example.starshipshop.domain.SimpleUserDto;
import com.example.starshipshop.domain.UserRequestInput;
import com.example.starshipshop.exception.AccountUsernameAlreadyExistException;
import com.example.starshipshop.exception.NonMatchingPasswordException;
import com.example.starshipshop.exception.TooManyUserPerAccountException;
import com.example.starshipshop.exception.UserPseudoAlreadyExistsException;
import com.example.starshipshop.repository.AccountRepository;
import com.example.starshipshop.repository.UserRepository;
import com.example.starshipshop.repository.jpa.user.Account;
import com.example.starshipshop.repository.jpa.user.Address;
import com.example.starshipshop.repository.jpa.user.Email;
import com.example.starshipshop.repository.jpa.user.Telephone;
import com.example.starshipshop.repository.jpa.user.User;
import com.example.starshipshop.repository.model.SecurityUserDetails;
import com.example.starshipshop.service.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RequiredArgsConstructor
@Service
@Slf4j
public class AccountService implements UserDetailsService {
    
    private final static int MAX_USER_COUNT_PER_ACCOUNT = 5;
    
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder encoder;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository
        .findByUsername(username)
        .map(SecurityUserDetails::new)
        .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    }
    
    /**
    * @param authentication
    * @return a {@link SecurityUserDetails} instance held by the principal stored in the given
    *         {@value authentication}
    * @throws IllegalArgumentException
    */
    private SecurityUserDetails getSecurityUserDetail(Authentication authentication) throws IllegalArgumentException {
        Assert.notNull(authentication, "Authentication informations are null.");
        SecurityUserDetails userDetails = (SecurityUserDetails) authentication.getPrincipal();
        Assert.notNull(userDetails, "Principal informations are null.");
        return userDetails;
    }
    
    public Account getAccount(Authentication authentication) {
        SecurityUserDetails userDetails = getSecurityUserDetail(authentication);
        return userDetails.getAccount();
    }
    
    public AccountDto getAccountDto(Authentication authentication) {
        return accountMapper.toAccountDto(getAccount(authentication));
    }
    
    public AccountDto registerNewAccount(
    RegisterNewAccountRequestInput rnari) throws AccountUsernameAlreadyExistException, NonMatchingPasswordException, IllegalArgumentException,
    NullPointerException {
        Assert.notNull(rnari, "Register new account resource is null");
        if(!rnari.getPassword().equals(rnari.getMatchingPassword())) {
            throw new NonMatchingPasswordException();
        }
        
        if (checkEmailExists(rnari.getUsername())) {
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
    
    public boolean checkEmailExists(String email) {
        Optional<Account> optAccount = accountRepository.findByUsername(email);
        return optAccount.isPresent() && optAccount.get() != null;
    }
    
    public SimpleUserDto getUserInfo(Authentication authentication, String pseudo) {
        Assert.notNull(pseudo, "Given pseudo cannot be null");
        AccountDto account = getAccountDto(authentication);
        SimpleUserDto result = null;
        for (SimpleUserDto u : account.getUsers()) {
            if (u.getPseudo().equals(pseudo)) {
                result = u;
                break;
            }
        }
        
        return result;
    }
    
    public SimpleUserDto createUser(Authentication authentication, UserRequestInput sri) throws TooManyUserPerAccountException, UserPseudoAlreadyExistsException {
        Assert.notNull(sri, "Given user information cannot be null");
        // Retrieve account info from authentication
        Account account = getAccount(authentication);
        
        // Apply business rules on user and save it
        if(account.getUsers().size() >= MAX_USER_COUNT_PER_ACCOUNT) {
            throw new TooManyUserPerAccountException();
        }
        for (User u : account.getUsers()) {
            if (u.getPseudo().equals(sri.getPseudo())) {
                throw new UserPseudoAlreadyExistsException("" + sri.getPseudo());
            }
        }

        // Add userToSave to Sets to create entity in cascade.
        User userToSave = accountMapper.fromUserRequestInput(sri);
        if(userToSave.getAddresses() != null && !userToSave.getAddresses().isEmpty()) {
            for (Address a : userToSave.getAddresses()) {
                a.setUser(userToSave);
            }
        }
        if(userToSave.getEmails() != null && !userToSave.getEmails().isEmpty()) {
            for (Email e : userToSave.getEmails()) {
                e.setUser(userToSave);
            }
        }
        if(userToSave.getTelephones() != null && !userToSave.getTelephones().isEmpty()) {
            for (Telephone t : userToSave.getTelephones()) {
                t.setUser(userToSave);
            }
        }
        userToSave.setAccount(account);
        User user = this.userRepository.save(userToSave);
        
        // Update security context
        if(user != null) {
            SecurityUserDetails securityUserDetailsFromDatabase = (SecurityUserDetails) loadUserByUsername(account.getUsername());
            Account updatedAccount = securityUserDetailsFromDatabase.getAccount();
            
            SecurityUserDetails s = (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            s.setAccount(updatedAccount);
        }
        
        return accountMapper.toSimpleUserDto(user);
    }
}
