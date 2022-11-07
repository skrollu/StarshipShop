package com.example.starshipShop.service;

import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.example.starshipShop.domain.AccountDto;
import com.example.starshipShop.repository.model.SecurityUserDetails;
import com.example.starshipShop.service.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountMapper accountMapper;

    public Optional<AccountDto> getMyAccount(Authentication authentication) {
        SecurityUserDetails userDetails = (SecurityUserDetails) authentication.getPrincipal();
        AccountDto result = null;
        if (userDetails != null) {
            result = accountMapper.toAccountDto(userDetails.getAccount());
        }
        return Optional.of(result);
    }
}
