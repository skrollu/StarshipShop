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
import org.springframework.util.Assert;

import com.example.starshipshop.common.exception.AccountUsernameAlreadyExistException;
import com.example.starshipshop.common.exception.NonMatchingPasswordException;
import com.example.starshipshop.common.exception.ResourceNotFoundException;
import com.example.starshipshop.common.exception.TooManyUserPerAccountException;
import com.example.starshipshop.common.exception.UserPseudoAlreadyExistsException;
import com.example.starshipshop.config.security.SecurityUserRole;
import com.example.starshipshop.domain.account.AccountOutput;
import com.example.starshipshop.domain.account.CreateAccountInput;
import com.example.starshipshop.domain.user.AddressOutput;
import com.example.starshipshop.domain.user.CreateUserAddressInput;
import com.example.starshipshop.domain.user.CreateUserEmailInput;
import com.example.starshipshop.domain.user.CreateUserInput;
import com.example.starshipshop.domain.user.CreateUserTelephoneInput;
import com.example.starshipshop.domain.user.EmailOutput;
import com.example.starshipshop.domain.user.SimpleUserOutput;
import com.example.starshipshop.domain.user.TelephoneOutput;
import com.example.starshipshop.domain.user.UpdateUserInput;
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

    private static final int MAX_USER_COUNT_PER_ACCOUNT = 5;
    private static final int MAX_ADDRESSES_PER_USER = 3;
    private static final int MAX_EMAILS_PER_USER = 3;
    private static final int MAX_TELEPHONES_PER_USER = 3;

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
     * @return the {@link User} with the {@code userId} held by the given
     *         {@code account}
     * @throws {@link ResourceNotFoundException} if no {@link User} found with the
     *                given {@code userId}
     */
    private User getUserFromAccount(Account account, Long userId) throws ResourceNotFoundException {
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
     * @return a {@link SecurityUserDetails} instance held by the principal stored
     *         in the given
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

    public AccountOutput getAccountDto(Authentication authentication) {
        return accountMapper.toAccountOutput(getAccount(authentication));
    }

    public AccountOutput createAccount(CreateAccountInput cai)
            throws AccountUsernameAlreadyExistException, NonMatchingPasswordException,
            IllegalArgumentException, NullPointerException {
        Assert.notNull(cai, "Create account resource is null");
        if (!cai.getPassword().equals(cai.getMatchingPassword())) {
            throw new NonMatchingPasswordException();
        }

        if (checkUsernameExists(cai.getUsername())) {
            throw new AccountUsernameAlreadyExistException(
                    "There is an account with that email address: " + cai.getUsername());
        }

        Account toSave = accountMapper.fromCreateAccountInput(cai);
        if (toSave == null) {
            throw new NullPointerException("Error when mapping account.");
        }
        toSave.setPassword(encoder.encode(toSave.getPassword()));
        toSave.setRoles(SecurityUserRole.USER.name());
        Account result = accountRepository.save(toSave);
        return this.accountMapper.toAccountOutput(result);
    }

    /**
     * Check in database if the given {@code username} exists in database
     */
    public boolean checkUsernameExists(String username) {
        Optional<Account> optAccount = accountRepository.findByUsername(username);
        return optAccount.isPresent() && optAccount.get() != null;
    }

    public SimpleUserOutput getUserInfo(Authentication authentication, Long id) {
        Assert.notNull(id, "Given id cannot be null");
        Account account = getAccount(authentication);
        User result = null;
        for (User u : account.getUsers()) {
            if (u.getId().equals(id)) {
                result = u;
                break;
            }
        }
        if (result == null) {
            throw new ResourceNotFoundException("No user found with the given id: " + idToHashConverter.convert(id));
        }
        return accountMapper.toSimpleUserOutput(result);
    }

    public SimpleUserOutput createUser(Authentication authentication, CreateUserInput cui)
            throws TooManyUserPerAccountException, UserPseudoAlreadyExistsException {
        Assert.notNull(cui, "Given user information cannot be null");
        // Retrieve account info from authentication
        Account account = getAccount(authentication);

        // Apply business rules on user
        if (account.getUsers().size() >= MAX_USER_COUNT_PER_ACCOUNT) {
            throw new TooManyUserPerAccountException();
        }
        for (User u : account.getUsers()) {
            if (u.getPseudo().equals(cui.getPseudo())) {
                throw new UserPseudoAlreadyExistsException(cui.getPseudo());
            }
        }

        // Add userToSave to Sets to create entity in cascade.
        User userToSave = accountMapper.fromCreateUserInput(cui);
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

        return accountMapper.toSimpleUserOutput(userToSave);
    }

    /**
     * Update the {@link User} entity in database according to {@code uui}.
     * Delete non given/non required datas, update those with a correct id and add
     * those with no id.
     * 
     * @return {@link SimpleUserOutput} which resume the given
     *         {@link UpdateUserInput}
     */
    public SimpleUserOutput updateUser(Authentication authentication, UpdateUserInput uui,
            Long userId) throws TooManyUserPerAccountException, @Valid UserPseudoAlreadyExistsException,
            ResourceNotFoundException {
        Assert.notNull(uui, "Given user information cannot be null");
        Assert.notNull(uui, "Given id cannot be null");
        // Retrieve account info from authentication
        Account account = getAccount(authentication);
        User userInDatabase = getUserFromAccount(account, userId);

        // Apply business rules on user
        // 2 users cannot have the same pseudo
        for (User u : account.getUsers()) {
            if (u.getPseudo().equals(uui.getPseudo()) && !u.getId().equals(userId)) {
                throw new UserPseudoAlreadyExistsException(uui.getPseudo());
            }
        }

        User userToSave = accountMapper.fromUpdateUserInput(uui);
        userToSave.setId(userInDatabase.getId());

        // Check for each given address id if it exist in database and add a reference
        // to userToSave
        // Then check if all addresses in db are given else add their ids in
        // addressesToRemove
        Set<Long> givenAddressesId = new HashSet<>();
        Set<Long> addressesIdToRemove = new HashSet<>();
        if (userToSave.getAddresses() != null && !userToSave.getAddresses().isEmpty()) {
            for (Address a : userToSave.getAddresses()) {
                // Add a reference to userToSave
                a.setUser(userToSave);
                // check if given address has an id
                if (a.getId() != null) {
                    if (givenAddressesId.contains(a.getId())) {
                        throw new IllegalArgumentException("Multiple addresses with the same id: "
                                + idToHashConverter.convert(a.getId()) + " given.");
                    }
                    givenAddressesId.add(a.getId());
                    // Check if given address id is already contained by the stored user
                    boolean givenAddressExists = false;
                    for (Address address : userInDatabase.getAddresses()) {
                        if (a.getId().equals(address.getId())) {
                            givenAddressExists = true;
                        }
                    }
                    if (!givenAddressExists) {
                        throw new ResourceNotFoundException("The address with the given id: "
                                + idToHashConverter.convert(a.getId()) + " doesn't exists.");
                    }
                }
            }
        }
        // Gather address to delete from database in addressesIdToRemove
        if (userInDatabase.getAddresses() != null && !userInDatabase.getAddresses().isEmpty()) {
            for (Address a : userInDatabase.getAddresses()) {
                if (!givenAddressesId.isEmpty()) {
                    if (!givenAddressesId.contains(a.getId())) {
                        addressesIdToRemove.add(a.getId());
                    }
                } else {
                    addressesIdToRemove.add(a.getId());
                }
            }
        }

        // Check for each given email id if it exist in database and add a reference to
        // userToSave
        // Then check if all emails in db are given else add their ids in
        // emailsIdToRemove
        Set<Long> givenEmailsId = new HashSet<>();
        Set<Long> emailsIdToRemove = new HashSet<>();
        if (userToSave.getEmails() != null && !userToSave.getEmails().isEmpty()) {
            for (Email e : userToSave.getEmails()) {
                // Add a reference to userToSave
                e.setUser(userToSave);
                // check if given email has an id
                if (e.getId() != null) {
                    if (givenEmailsId.contains(e.getId())) {
                        throw new IllegalArgumentException("Multiple emails with the same id: "
                                + idToHashConverter.convert(e.getId()) + " given.");
                    }
                    givenEmailsId.add(e.getId());
                    // Check if given email id is already contained by the stored user
                    boolean givenEmailExists = false;
                    for (Email email : userInDatabase.getEmails()) {
                        if (e.getId().equals(email.getId())) {
                            givenEmailExists = true;
                        }
                    }
                    if (!givenEmailExists) {
                        throw new ResourceNotFoundException("The email with the given id: "
                                + idToHashConverter.convert(e.getId()) + " doesn't exists.");
                    }
                }
            }
        }
        // Gather email to delete from database in emailsIdToRemove
        if (userInDatabase.getEmails() != null && !userInDatabase.getEmails().isEmpty()) {
            for (Email e : userInDatabase.getEmails()) {
                if (!givenEmailsId.isEmpty()) {
                    if (!givenEmailsId.contains(e.getId())) {
                        emailsIdToRemove.add(e.getId());
                    }
                } else {
                    emailsIdToRemove.add(e.getId());
                }
            }
        }

        // Check for each given telephone id if it exist in database and add a reference
        // to userToSave
        // Then check if all telephones in db are given else add their ids in
        // telephonesIdToRemove
        Set<Long> givenTelephonesId = new HashSet<>();
        Set<Long> telephonesIdToRemove = new HashSet<>();
        if (userToSave.getTelephones() != null && !userToSave.getTelephones().isEmpty()) {
            for (Telephone t : userToSave.getTelephones()) {
                // Add a reference to userToSave
                t.setUser(userToSave);
                // check if given telephone has an id
                if (t.getId() != null) {
                    if (givenTelephonesId.contains(t.getId())) {
                        throw new IllegalArgumentException("Multiple telephones with the same id: "
                                + idToHashConverter.convert(t.getId()) + " given.");
                    }
                    givenTelephonesId.add(t.getId());
                    // Check if given telephone id is already contained by the stored user
                    boolean givenTelephoneExists = false;
                    for (Telephone telephone : userInDatabase.getTelephones()) {
                        if (t.getId().equals(telephone.getId())) {
                            givenTelephoneExists = true;
                        }
                    }
                    if (!givenTelephoneExists) {
                        throw new ResourceNotFoundException("The telephone with the given id: "
                                + idToHashConverter.convert(t.getId()) + " doesn't exists.");
                    }
                }
            }
        }
        // Gather telephone to delete from database in telephonesIdToRemove
        if (userInDatabase.getTelephones() != null && !userInDatabase.getTelephones().isEmpty()) {
            for (Telephone t : userInDatabase.getTelephones()) {
                if (!givenTelephonesId.isEmpty()) {
                    if (!givenTelephonesId.contains(t.getId())) {
                        telephonesIdToRemove.add(t.getId());
                    }
                } else {
                    telephonesIdToRemove.add(t.getId());
                }
            }
        }

        // // Remove non given entities
        if (!addressesIdToRemove.isEmpty()) {
            addressesIdToRemove.stream().forEach(addressRepository::deleteById);
        }
        if (!emailsIdToRemove.isEmpty()) {
            emailsIdToRemove.stream().forEach(emailRepository::deleteById);
        }
        if (!telephonesIdToRemove.isEmpty()) {
            telephonesIdToRemove.stream().forEach(telephoneRepository::deleteById);
        }
        // Save entities
        userToSave.setAccount(account);
        this.userRepository.save(userToSave);
        this.addressRepository.saveAll(userToSave.getAddresses());
        this.emailRepository.saveAll(userToSave.getEmails());
        this.telephoneRepository.saveAll(userToSave.getTelephones());

        // Update user entity and security context by reference userInDatabase come from
        // it.
        BeanUtils.copyProperties(userToSave, userInDatabase);

        return accountMapper.toSimpleUserOutput(userToSave);
    }

    /**
     * Reload the {@link Account} from database and update it in spring security
     * context.
     * Update security context is recommanded on {@link Account} update
     * in order to keep context in phase with the database.
     * 
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

    public EmailOutput createUserEmail(Authentication authentication, CreateUserEmailInput cuei, Long userId)
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
        Email emailToSave = accountMapper.fromCreateUserEmailInput(cuei);
        emailToSave.setUser(user);
        Email result = emailRepository.save(emailToSave);

        // Update security context
        this.updateSecurityContext(authenticatedAccount.getUsername());

        return accountMapper.toEmailOutput(result);
    }

    public TelephoneOutput createUserTelephone(Authentication authentication, CreateUserTelephoneInput cuti,
            Long userId)
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
        Telephone telephoneToSave = accountMapper.fromCreateUserTelephoneInput(cuti);
        telephoneToSave.setUser(user);
        Telephone result = telephoneRepository.save(telephoneToSave);

        // Update security context
        this.updateSecurityContext(authenticatedAccount.getUsername());

        return accountMapper.toTelephoneOutput(result);
    }

    public AddressOutput createUserAddress(Authentication authentication, CreateUserAddressInput cuai,
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
        Address addressToSave = accountMapper.fromCreateUserAddressInput(cuai);
        addressToSave.setUser(user);

        Address result = addressRepository.save(addressToSave);

        // Update security context
        this.updateSecurityContext(authenticatedAccount.getUsername());

        return accountMapper.toAddressOutput(result);
    }

}
