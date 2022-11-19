package com.example.starshipshop.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.starshipshop.domain.AccountDto;
import com.example.starshipshop.domain.AddressDto;
import com.example.starshipshop.domain.EmailDto;
import com.example.starshipshop.domain.RegisterNewAccountRequestInput;
import com.example.starshipshop.domain.SimpleUserDto;
import com.example.starshipshop.domain.TelephoneDto;
import com.example.starshipshop.domain.UserRequestInput;
import com.example.starshipshop.repository.jpa.user.Account;
import com.example.starshipshop.repository.jpa.user.Address;
import com.example.starshipshop.repository.jpa.user.Email;
import com.example.starshipshop.repository.jpa.user.Telephone;
import com.example.starshipshop.repository.jpa.user.User;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", ignore = true)
    Account toAccount(AccountDto dto);

    AccountDto toAccountDto(Account account);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    Account fromRegisterNewAccountRequestInput(RegisterNewAccountRequestInput input);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "account", ignore = true)
    @Mapping(target = "addresses")
    User toUser(SimpleUserDto dto);

    SimpleUserDto toSimpleUserDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "account", ignore = true)
    User fromUserRequestInput(UserRequestInput uri);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Address toAddress(AddressDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Email toEmail(EmailDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "telephoneNumber", source = "telephoneNumber")
    Telephone toTelephone(TelephoneDto dto);
}
