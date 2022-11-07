package com.example.starshipshop.service;

import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.example.starshipshop.domain.AccountDto;
import com.example.starshipshop.repository.model.SecurityUserDetails;
import com.example.starshipshop.service.mapper.AccountMapper;
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
