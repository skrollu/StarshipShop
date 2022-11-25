package com.example.starshipshop.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.validation.Valid;
import org.hibernate.jdbc.TooManyRowsAffectedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
import com.example.starshipshop.domain.CreateUserRequestInput;
import com.example.starshipshop.domain.EmailDto;
import com.example.starshipshop.domain.EmailRequestInput;
import com.example.starshipshop.domain.RegisterNewAccountRequestInput;
import com.example.starshipshop.domain.SimpleUserDto;
import com.example.starshipshop.domain.TelephoneDto;
import com.example.starshipshop.domain.UpdateUserTelephoneRequestInput;
import com.example.starshipshop.domain.UpdateUserRequestInput;
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
    
    public SimpleUserDto createUser(Authentication authentication, CreateUserRequestInput curi)
    throws TooManyUserPerAccountException, UserPseudoAlreadyExistsException {
        Assert.notNull(curi, "Given user information cannot be null");
        // Retrieve account info from authentication
        Account account = getAccount(authentication);
        
        // Apply business rules on user
        if (account.getUsers().size() >= MAX_USER_COUNT_PER_ACCOUNT) {
            throw new TooManyUserPerAccountException();
        }
        for (User u : account.getUsers()) {
            if (u.getPseudo().equals(curi.getPseudo())) {
                throw new UserPseudoAlreadyExistsException(curi.getPseudo());
            }
        }
            
        // Add userToSave to Sets to create entity in cascade.
        User userToSave = accountMapper.fromCreateUserRequestInput(curi);
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
    public SimpleUserDto updateUser(Authentication authentication, UpdateUserRequestInput uuri,
    Long userId) throws TooManyUserPerAccountException, @Valid UserPseudoAlreadyExistsException,
    ResourceNotFoundException {
        Assert.notNull(uuri, "Given user information cannot be null");
        Assert.notNull(uuri, "Given id cannot be null");
        // Retrieve account info from authentication
        Account account = getAccount(authentication);
        User userInDatabase = getUserFromAccount(account, userId);

        // Apply business rules on user
        // 2 users cannot have the same pseudo
        for (User u : account.getUsers()) {
            if (u.getPseudo().equals(uuri.getPseudo()) && !u.getId().equals(userId)) {
                throw new UserPseudoAlreadyExistsException(uuri.getPseudo());
            }
        }
        
        User userToSave = accountMapper.fromUpdateUserRequestInput(uuri);
        userToSave.setId(userInDatabase.getId());
        
        // Check if given address id exist and add a reference to userToSave
        // Then check if all addresses in db are given else add there ids in addressesIdToRemove
        // Set<Long> addressesIdToRemove = new HashSet<>();
        // if (userToSave.getAddresses() != null && !userToSave.getAddresses().isEmpty()) {
        //     Set<Long> givenAddressesId = new HashSet<>();
        //     for(Address a: userToSave.getAddresses()) {
        //         // Add a reference to userToSave
        //         a.setUser(userToSave);
        //         if(a.getId() != null ) {
        //             givenAddressesId.add(userId);
        //         }
        //     }
        //     for(Address a: userInDatabase.getAddresses()) {
        //         if(!givenAddressesId.contains(a.getId())) {
        //             throw new ResourceNotFoundException("Cannot retrieve address with the given id: " + idToHashConverter.convert(a.getId()));
        //         }
        //         for(Address i: userToSave.getAddresses()) {
        //             if(!i.getId().equals(a.getId())) {
        //                 addressesIdToRemove.add(a.getId());
        //             }
        //         }
        //     }
        // }
        
        // Check for each given email id if it exist in database and add a reference to userToSave
        // Then check if all emails in db are given else add there ids in emailsIdToRemove
        Set<Long> emailsIdToRemove = new HashSet<>();
        if (userToSave.getEmails() != null && !userToSave.getEmails().isEmpty()) {
            Set<Long> givenEmailsId = new HashSet<>();
            for (Email e : userToSave.getEmails()) {
                // Add a reference to userToSave
                e.setUser(userToSave);
                if (e.getId() != null) {
                    givenEmailsId.add(userId);
                }
            }
            for (Email e : userInDatabase.getEmails()) {
                if (!givenEmailsId.contains(e.getId())) {
                    throw new ResourceNotFoundException(
                            "Cannot retrieve email with the given id: "
                                    + idToHashConverter.convert(e.getId()));
                }
                for (Email i : userToSave.getEmails()) {
                    if (i.getId() != null && !i.getId().equals(e.getId())) {
                        emailsIdToRemove.add(e.getId());
                    }
                }
            }
        }
        // // Check if every ids in db are given else remove
        // List<Long> addressesIdToRemove = new ArrayList<>();
        // if(userInDatabase.getAddresses() != null && !userInDatabase.getAddresses().isEmpty()) {
        //     for(Address aInDb: userInDatabase.getAddresses()) {
        //         boolean isAInDbGiven = false;
        //         for (Address a : userToSave.getAddresses()) {
        //             if (a.getId() != null) {
        //                 if(aInDb.getId().equals(a.getId())) {
        //                     isAInDbGiven = true;
        //                 }
        //                 if(!isAInDbGiven) {
        //                     addressesIdToRemove.add(aInDb.getId());
        //                 }
        //             }
        //         }
        //     }
        // }
        
        // // Check if given email id exists in db.
        // if (userToSave.getEmails() != null && !userToSave.getEmails().isEmpty()) {
        //     for (Email e : userToSave.getEmails()) {
        //         // Add a reference to userToSaveEntity
        //         e.setUser(userToSave);
        //         if (e.getId() != null && userInDatabase.getEmails() != null
        //         && !userInDatabase.getEmails().isEmpty()) {
        //             for (Email eInDb : userInDatabase.getEmails()) {
        //                 if (!e.getId().equals(eInDb.getId())) {
        //                     throw new ResourceNotFoundException(
        //                     "Cannot retrieve email with the given id: "
        //                     + idToHashConverter.convert(e.getId()));
        //                 }
        //             }
        //         }
        //     }
        // }
        
        // // Check if every ids in db are given else remove
        // List<Long> emailsIdToRemove = new ArrayList<>();
        // if (userInDatabase.getEmails() != null && !userInDatabase.getEmails().isEmpty()) {
        //     for (Email eInDb : userInDatabase.getEmails()) {
        //         boolean isEInDbGiven = false;
        //         for (Email e : userToSave.getEmails()) {
        //             if (e.getId() != null) {
        //                 if (eInDb.getId().equals(e.getId())) {
        //                     isEInDbGiven = true;
        //                 }
        //                 if (!isEInDbGiven) {
        //                     addressesIdToRemove.add(eInDb.getId());
        //                 }
        //             }
        //         }
        //     }
        // }
        
        // // Check if given telephones id exists in db.
        // if (userToSave.getTelephones() != null && !userToSave.getTelephones().isEmpty()) {
        //     for (Telephone t : userToSave.getTelephones()) {
        //         // Add a reference to userToSaveEntity
        //         t.setUser(userToSave);
        //         if (t.getId() != null && userInDatabase.getTelephones() != null
        //         && !userInDatabase.getTelephones().isEmpty()) {
        //             for (Telephone tInDb : userInDatabase.getTelephones()) {
        //                 if (!t.getId().equals(tInDb.getId())) {
        //                     throw new ResourceNotFoundException(
        //                     "Cannot retrieve telephone with the given id: "
        //                     + idToHashConverter.convert(t.getId()));
        //                 }
        //             }
        //         }
        //     }
        // }
        
        // // Check if every ids in db are given else remove
        // List<Long> telephonesIdToRemove = new ArrayList<>();
        // if (userInDatabase.getTelephones() != null && !userInDatabase.getTelephones().isEmpty())
        // {
        //     for (Telephone tInDb : userInDatabase.getTelephones()) {
        //         boolean isTInDbGiven = false;
        //         for (Telephone t : userToSave.getTelephones()) {
        //             if (t.getId() != null) {
        //                 if (tInDb.getId().equals(t.getId())) {
        //                     isTInDbGiven = true;
        //                 }
        //                 if (!isTInDbGiven) {
        //                     addressesIdToRemove.add(tInDb.getId());
        //                 }
        //             }
        //         }
        //     }
        // }
        
        // // Remove non given entities
        // addressesIdToRemove.stream().forEach(addressRepository::deleteById);
        // emailsIdToRemove.stream().forEach(emailRepository::deleteById);
        // telephonesIdToRemove.stream().forEach(telephoneRepository::deleteById);
        
        // Save entities
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
    * Reload the {@link Account} from database and update it in spring security context.
    * Update security context is recommanded on {@link Account} update
    * in order to keep context in phase with the database.
    */
    private void updateSecurityContext(String username) {
        log.info("Update security context");
        Assert.notNull(username, "Username must not be null");
        SecurityUserDetails securityUserDetailsFromDatabase =
        (SecurityUserDetails) loadUserByUsername(username);
        Account updatedAccount = securityUserDetailsFromDatabase.getAccount();
        SecurityUserDetails s = (SecurityUserDetails) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
        s.setAccount(updatedAccount);
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
    
    
    public TelephoneDto createUserTelephone(Authentication authentication, UpdateUserTelephoneRequestInput tri, Long userId)
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
        Telephone telephoneToSave = accountMapper.fromTelephoneRequestInput(tri);
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
