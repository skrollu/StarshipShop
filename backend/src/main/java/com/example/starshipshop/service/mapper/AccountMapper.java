package com.example.starshipshop.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
import com.example.starshipshop.repository.jpa.user.Account;
import com.example.starshipshop.repository.jpa.user.Address;
import com.example.starshipshop.repository.jpa.user.Email;
import com.example.starshipshop.repository.jpa.user.Telephone;
import com.example.starshipshop.repository.jpa.user.User;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountOutput toAccountOutput(Account account);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    Account fromCreateAccountInput(CreateAccountInput input);

    SimpleUserOutput toSimpleUserOutput(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "account", ignore = true)
    User fromCreateUserInput(CreateUserInput cui);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "account", ignore = true)
    User fromUpdateUserInput(UpdateUserInput uui);

    AddressOutput toAddressOutput(Address address);

    @Mapping(target = "id", ignore = true)
    Address fromCreateUserAddressInput(CreateUserAddressInput cuai);

    EmailOutput toEmailOutput(Email email);

    @Mapping(target = "id", ignore = true)
    Email fromCreateUserEmailInput(CreateUserEmailInput cuei);

    TelephoneOutput toTelephoneOutput(Telephone telephone);

    @Mapping(target = "id", ignore = true)
    Telephone fromCreateUserTelephoneInput(CreateUserTelephoneInput cuti);
}
