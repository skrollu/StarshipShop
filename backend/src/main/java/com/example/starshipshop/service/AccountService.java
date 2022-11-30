package com.example.starshipshop.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.hibernate.jdbc.TooManyRowsAffectedException;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.example.starshipshop.common.exception.AccountUsernameAlreadyExistException;
import com.example.starshipshop.common.exception.NonMatchingPasswordException;
import com.example.starshipshop.common.exception.ResourceNotFoundException;
import com.example.starshipshop.common.exception.TooManyUserPerAccountException;
import com.example.starshipshop.common.exception.UserPseudoAlreadyExistsException;
import com.example.starshipshop.config.security.SecurityUserRole;
import com.example.starshipshop.domain.AccountDto;
import com.example.starshipshop.domain.AddressDto;
import com.example.starshipshop.domain.AddressRequestInput;
import com.example.starshipshop.domain.CreateUserInputRequest;
import com.example.starshipshop.domain.EmailDto;
import com.example.starshipshop.domain.EmailRequestInput;
import com.example.starshipshop.domain.RegisterNewAccountRequestInput;
import com.example.starshipshop.domain.SimpleUserDto;
import com.example.starshipshop.domain.TelephoneDto;
import com.example.starshipshop.domain.TelephoneInputRequest;
import com.example.starshipshop.domain.UpdateUserInputRequest;
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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RequiredArgsConstructor
@Service
@Slf4j
public class AccountService implements UserDetailsService {
    
    private final static int MAX_USER_COUNT_PER_ACCOUNT = 5;
    private final static int MAX_ADDRESSES_PER_USER = 3;
    private final static int MAX_EMAILS_PER_USER = 3;
    private final static int MAX_TELEPHONES_PER_USER = 3;
    
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final EmailRepository emailRepository;
    private final TelephoneRepository telephoneRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder encoder;
    private final IdToHashConverter idToHashConverter;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByUsername(username).map(SecurityUserDetails::new).orElseThrow(
        () -> new UsernameNotFoundException("Username not found: " + username));
    }
    
    /**
    * @param account
    * @param userId
    * @return the {@link User} with the {@code userId} held by the given {@code account}
    * @throws {@link ResourceNotFoundException} if no {@link User} found with the given {@code userId}
    */
    private User getUserFromAccount(Account account, Long userId)  throws ResourceNotFoundException {
        if (account.getUsers() == null || account.getUsers().isEmpty()) {
            throw new ResourceNotFoundException("The authenticated account has no user defined");
        }
        
        return account.getUsers().stream().filter(u -> u.getId().equals(userId))
        .findFirst()
        .orElseThrow(() -> new ResourceNotFoundException(
        "Cannot retrieve user with the given id: "
        + idToHashConverter.convert(userId)));
    }
    
    /**
    * @param authentication
    * @return a {@link SecurityUserDetails} instance held by the principal stored in the given
    *         {@value authentication}
    * @throws IllegalArgumentException
    */
    private SecurityUserDetails getSecurityUserDetail(Authentication authentication)
    throws IllegalArgumentException {
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
    
    public AccountDto registerNewAccount(RegisterNewAccountRequestInput rnari)
    throws AccountUsernameAlreadyExistException, NonMatchingPasswordException,
    IllegalArgumentException, NullPointerException {
        Assert.notNull(rnari, "Register new account resource is null");
        if (!rnari.getPassword().equals(rnari.getMatchingPassword())) {
            throw new NonMatchingPasswordException();
        }
        
        if (checkUsernameExists(rnari.getUsername())) {
            throw new AccountUsernameAlreadyExistException(
            "There is an account with that email address: " + rnari.getUsername());
        }
        
        Account toSave = accountMapper.fromRegisterNewAccountRequestInput(rnari);
        if (toSave == null) {
            throw new NullPointerException("Error when mapping account.");
        }
        toSave.setPassword(encoder.encode(toSave.getPassword()));
        toSave.setRoles(SecurityUserRole.USER.name());
        Account result = accountRepository.save(toSave);
        return this.accountMapper.toAccountDto(result);
    }
    
    /**
    * Check in database if the given {@code username} exists in database
    */
    public boolean checkUsernameExists(String username) {
        Optional<Account> optAccount = accountRepository.findByUsername(username);
        return optAccount.isPresent() && optAccount.get() != null;
    }
    
    public SimpleUserDto getUserInfo(Authentication authentication, Long id) {
        Assert.notNull(id, "Given id cannot be null");
        AccountDto account = getAccountDto(authentication);
        SimpleUserDto result = null;
        for (SimpleUserDto u : account.getUsers()) {
            if (u.getId().equals(id)) {
                result = u;
                break;
            }
        }
        
        return result;
    }
    
    public SimpleUserDto createUser(Authentication authentication, CreateUserInputRequest cuir) throws TooManyUserPerAccountException, UserPseudoAlreadyExistsException {
        Assert.notNull(cuir, "Given user information cannot be null");
        // Retrieve account info from authentication
        Account account = getAccount(authentication);
        
        // Apply business rules on user
        if (account.getUsers().size() >= MAX_USER_COUNT_PER_ACCOUNT) {
            throw new TooManyUserPerAccountException();
        }
        for (User u : account.getUsers()) {
            if (u.getPseudo().equals(cuir.getPseudo())) {
                throw new UserPseudoAlreadyExistsException(cuir.getPseudo());
            }
        }
        
        // Add userToSave to Sets to create entity in cascade.
        User userToSave = accountMapper.fromCreateUserInputRequest(cuir);
        if (userToSave.getAddresses() != null && !userToSave.getAddresses().isEmpty()) {
            for (Address a : userToSave.getAddresses()) {
                a.setUser(userToSave);
            }
        }
        if (userToSave.getEmails() != null && !userToSave.getEmails().isEmpty()) {
            for (Email e : userToSave.getEmails()) {
                e.setUser(userToSave);
            }
        }
        if (userToSave.getTelephones() != null && !userToSave.getTelephones().isEmpty()) {
            for (Telephone t : userToSave.getTelephones()) {
                t.setUser(userToSave);
            }
        }
        userToSave.setAccount(account);
        userToSave = this.userRepository.save(userToSave);
        this.addressRepository.saveAll(userToSave.getAddresses());
        this.emailRepository.saveAll(userToSave.getEmails());
        this.telephoneRepository.saveAll(userToSave.getTelephones());
        
        // Update security context
        this.updateSecurityContext(account.getUsername());
        
        return accountMapper.toSimpleUserDto(userToSave);
    }
    
    /**
    * On delete tout ce qui n'est pas fournit 
    * pas de merge
    * pas de comparaison de variable egale sauf pseudo
    */
    public SimpleUserDto updateUser(Authentication authentication, UpdateUserInputRequest uuir,
    Long userId) throws TooManyUserPerAccountException, @Valid UserPseudoAlreadyExistsException,
    ResourceNotFoundException {
        Assert.notNull(uuir, "Given user information cannot be null");
        Assert.notNull(uuir, "Given id cannot be null");
        // Retrieve account info from authentication
        Account account = getAccount(authentication);
        User userInDatabase = getUserFromAccount(account, userId);
        
        // Apply business rules on user
        // 2 users cannot have the same pseudo
        for (User u : account.getUsers()) {
            if (u.getPseudo().equals(uuir.getPseudo()) && !u.getId().equals(userId)) {
                throw new UserPseudoAlreadyExistsException(uuir.getPseudo());
            }
        }
        
        User userToSave = accountMapper.fromUpdateUserInputRequest(uuir);
        userToSave.setId(userInDatabase.getId());
        
        // Check for each given address id if it exist in database and add a reference to userToSave
        // Then check if all addresses in db are given else add their ids in addressesToRemove
        Set<Long> givenAddressesId = new HashSet<>();
        Set<Long> addressesIdToRemove = new HashSet<>();
        if (userToSave.getAddresses() != null && !userToSave.getAddresses().isEmpty()) {
            for(Address a : userToSave.getAddresses()) {
                // Add a reference to userToSave
                a.setUser(userToSave);
                // check if given address has an id
                if(a.getId() != null) {
                    givenAddressesId.add(a.getId());
                    // Check if given address id is already contained by the stored user 
                    boolean givenAddressExists = false;
                    for(Address address: userInDatabase.getAddresses()) {
                        if(a.getId().equals(address.getId())){
                            givenAddressExists = true;
                        }
                    }
                    if(!givenAddressExists) {
                        throw new ResourceNotFoundException("The address with the given id: " + idToHashConverter.convert(a.getId()) + " doesn't exists.");
                    }
                }
            }
        }
        // Gather address to delete from database in addressesIdToRemove
        if(userInDatabase.getAddresses() != null && !userInDatabase.getAddresses().isEmpty()) {
            for(Address a: userInDatabase.getAddresses()) {
                if(!givenAddressesId.isEmpty()) {
                    if(!givenAddressesId.contains(a.getId())) {
                        addressesIdToRemove.add(a.getId());
                    }
                } else {
                    addressesIdToRemove.add(a.getId());
                }
            }
        }

        // Check for each given email id if it exist in database and add a reference to userToSave
        // Then check if all emails in db are given else add their ids in emailsIdToRemove
        Set<Long> givenEmailsId = new HashSet<>();
        Set<Long> emailsIdToRemove = new HashSet<>();
        if (userToSave.getEmails() != null && !userToSave.getEmails().isEmpty()) {
            for(Email e : userToSave.getEmails()) {
                // Add a reference to userToSave
                e.setUser(userToSave);
                // check if given email has an id
                if(e.getId() != null) {
                    givenEmailsId.add(e.getId());
                    // Check if given email id is already contained by the stored user 
                    boolean givenEmailExists = false;
                    for(Email email: userInDatabase.getEmails()) {
                        if(e.getId().equals(email.getId())){
                            givenEmailExists = true;
                        }
                    }
                    if(!givenEmailExists) {
                        throw new ResourceNotFoundException("The email with the given id: " + idToHashConverter.convert(e.getId()) + " doesn't exists.");
                    }
                }
            }
        }
        // Gather email to delete from database in emailsIdToRemove
        if(userInDatabase.getEmails() != null && !userInDatabase.getEmails().isEmpty()) {
            for(Email e: userInDatabase.getEmails()) {
                if(!givenEmailsId.isEmpty()) {
                    if(!givenEmailsId.contains(e.getId())) {
                        emailsIdToRemove.add(e.getId());
                    }
                } else {
                    emailsIdToRemove.add(e.getId());
                }
            }
        }

        // Check for each given telephone id if it exist in database and add a reference to userToSave
        // Then check if all telephones in db are given else add their ids in telephonesIdToRemove
        Set<Long> givenTelephonesId = new HashSet<>();
        Set<Long> telephonesIdToRemove = new HashSet<>();
        if (userToSave.getTelephones() != null && !userToSave.getTelephones().isEmpty()) {
            for(Telephone t : userToSave.getTelephones()) {
                // Add a reference to userToSave
                t.setUser(userToSave);
                // check if given telephone has an id
                if(t.getId() != null) {
                    givenTelephonesId.add(t.getId());
                    // Check if given telephone id is already contained by the stored user 
                    boolean givenTelephoneExists = false;
                    for(Telephone telephone: userInDatabase.getTelephones()) {
                        if(t.getId().equals(telephone.getId())){
                            givenTelephoneExists = true;
                        }
                    }
                    if(!givenTelephoneExists) {
                        throw new ResourceNotFoundException("The telephone with the given id: " + idToHashConverter.convert(t.getId()) + " doesn't exists.");
                    }
                }
            }
        }
        // Gather telephone to delete from database in telephonesIdToRemove
        if(userInDatabase.getTelephones() != null && !userInDatabase.getTelephones().isEmpty()) {
            for(Telephone t: userInDatabase.getTelephones()) {
                if(!givenTelephonesId.isEmpty()) {
                    if(!givenTelephonesId.contains(t.getId())) {
                        telephonesIdToRemove.add(t.getId());
                    }
                } else {
                    telephonesIdToRemove.add(t.getId());
                }
            }
        }

        // // Remove non given entities
        if(!addressesIdToRemove.isEmpty()) {
            addressesIdToRemove.stream().forEach(addressRepository::deleteById);
        }
        if(!emailsIdToRemove.isEmpty()) {
            emailsIdToRemove.stream().forEach(emailRepository::deleteById);
        }
        if(!telephonesIdToRemove.isEmpty()) {
            telephonesIdToRemove.stream().forEach(telephoneRepository::deleteById);
        }
        // Save entities
        userToSave.setAccount(account);
        this.userRepository.save(userToSave);
        this.addressRepository.saveAll(userToSave.getAddresses());
        this.emailRepository.saveAll(userToSave.getEmails());
        this.telephoneRepository.saveAll(userToSave.getTelephones());
        
        User result = this.userRepository.getReferenceById(userId);
        // Update user entity and security context by reference userInDatabase come from it.
        BeanUtils.copyProperties(result, userInDatabase);

        return accountMapper.toSimpleUserDto(result);
    }
    
    /**
    * Reload the {@link Account} from database and update it in spring security context.
    * Update security context is recommanded on {@link Account} update
    * in order to keep context in phase with the database.
    * @param username load account using it.
    */
    private void updateSecurityContext(String username) {
        Assert.notNull(username, "Username must not be null");
        SecurityUserDetails securityUserDetailsFromDatabase = (SecurityUserDetails) loadUserByUsername(username);
        Account updatedAccount = securityUserDetailsFromDatabase.getAccount();
        SecurityUserDetails s = (SecurityUserDetails) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
        s.setAccount(updatedAccount);
        log.info("Update security context");
    }

    /**
    * Update security context is recommanded on {@link Account} update
    * in order to keep context in phase with the database.
    */
    private void updateSecurityContext(Account account) {
        Assert.notNull(account, "Account must not be null");
        SecurityUserDetails s = (SecurityUserDetails) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
        s.setAccount(account);
        log.info("Update security context");
    }
    
    public EmailDto createUserEmail(Authentication authentication, EmailRequestInput eri, Long userId)
    throws ResourceNotFoundException {
        Account authenticatedAccount = getAccount(authentication);
        User user = getUserFromAccount(authenticatedAccount, userId);
        
        if (user.getEmails() != null && !user.getEmails().isEmpty()
        && user.getEmails().size() >= MAX_EMAILS_PER_USER) {
            log.warn("A user cannot have more than " + MAX_EMAILS_PER_USER + " emails.");
            throw new TooManyRowsAffectedException(
            "A user cannot have more than " + MAX_EMAILS_PER_USER + " emails.",
            MAX_EMAILS_PER_USER, 4);
        }
        Email emailToSave = accountMapper.fromEmailRequestInput(eri);
        emailToSave.setUser(user);
        Email result = emailRepository.save(emailToSave);
        
        // Update security context
        this.updateSecurityContext(authenticatedAccount.getUsername());
        
        return accountMapper.toEmailDto(result);
    }
    
    
    public TelephoneDto createUserTelephone(Authentication authentication, TelephoneInputRequest tir, Long userId)
    throws ResourceNotFoundException {
        Account authenticatedAccount = getAccount(authentication);
        User user = getUserFromAccount(authenticatedAccount, userId);
        
        if (user.getTelephones() != null && !user.getTelephones().isEmpty()
        && user.getTelephones().size() >= MAX_TELEPHONES_PER_USER) {
            log.warn("A user cannot have more than " + MAX_TELEPHONES_PER_USER + " telephone number.");
            throw new TooManyRowsAffectedException(
            "A user cannot have more than " + MAX_TELEPHONES_PER_USER + " telephone number.",
            MAX_TELEPHONES_PER_USER, 4);
        }
        Telephone telephoneToSave = accountMapper.fromTelephoneRequestInput(tir);
        telephoneToSave.setUser(user);
        Telephone result = telephoneRepository.save(telephoneToSave);
        
        // Update security context
        this.updateSecurityContext(authenticatedAccount.getUsername());
        
        return accountMapper.toTelephoneDto(result);
    }
    
    public AddressDto createUserAddress(Authentication authentication, AddressRequestInput ari,
    Long userId) throws ResourceNotFoundException {
        Account authenticatedAccount = getAccount(authentication);
        User user = getUserFromAccount(authenticatedAccount, userId);
        
        if (user.getAddresses() != null && !user.getAddresses().isEmpty()
        && user.getAddresses().size() >= MAX_ADDRESSES_PER_USER) {
            log.warn("A user cannot have more than " + MAX_ADDRESSES_PER_USER
            + " addresses.");
            throw new TooManyRowsAffectedException("A user cannot have more than "
            + MAX_ADDRESSES_PER_USER + " addresses.", MAX_ADDRESSES_PER_USER, 4);
        }
        Address addressToSave = accountMapper.fromAddressRequestInput(ari);
        addressToSave.setUser(user);
        
        Address result = addressRepository.save(addressToSave);
        
        // Update security context
        this.updateSecurityContext(authenticatedAccount.getUsername());
        
        return accountMapper.toAddressDto(result);
    }
    
    
}
