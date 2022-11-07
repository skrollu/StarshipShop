package com.example.starshipshop.service.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.example.starshipshop.domain.AccountDto;
import com.example.starshipshop.domain.AddressDto;
import com.example.starshipshop.domain.EmailDto;
import com.example.starshipshop.domain.SimpleUserDto;
import com.example.starshipshop.domain.TelephoneDto;
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
    @Mapping(target = "account", ignore = true)
    User toUser(SimpleUserDto dto);

    SimpleUserDto toUserDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Address toAddress(AddressDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Email toEmail(EmailDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Telephone toTelephone(TelephoneDto dto);
}
