package com.example.starshipshop.config;

import javax.annotation.PostConstruct;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.starshipshop.repository.AccountRepository;
import com.example.starshipshop.repository.jpa.user.Account;
import com.example.starshipshop.service.AccountService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCreator {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final PasswordEncoder encoder;

    @PostConstruct
    public void init() {
        // // TODO Temporal solution
        // accountRepository.deleteAll();

        // Account account =
        // Account.builder().username("user@mail.com").password(encoder.encode("password")).roles("USER").build();
        // accountRepository.save(account);
        // accountRepository.save(Account.builder().username("admin@mail.com").password(encoder.encode("password")).roles("USER,
        // ADMIN").build());

        // log.warn("\n\nGenerated password: " + encoder.encode("password") + "\n\n");
    }
}
