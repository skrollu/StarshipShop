package com.starshipshop.starshipservice.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.starshipshop.starshipservice.domain.account.AccountOutput;
import com.starshipshop.starshipservice.domain.account.CreateAccountInput;
import com.starshipshop.starshipservice.domain.user.AddressOutput;
import com.starshipshop.starshipservice.domain.user.CreateUserAddressInput;
import com.starshipshop.starshipservice.domain.user.CreateUserEmailInput;
import com.starshipshop.starshipservice.domain.user.CreateUserInput;
import com.starshipshop.starshipservice.domain.user.CreateUserTelephoneInput;
import com.starshipshop.starshipservice.domain.user.EmailOutput;
import com.starshipshop.starshipservice.domain.user.SimpleUserOutput;
import com.starshipshop.starshipservice.domain.user.TelephoneOutput;
import com.starshipshop.starshipservice.domain.user.UpdateUserInput;
import com.starshipshop.starshipservice.repository.jpa.user.Account;
import com.starshipshop.starshipservice.repository.jpa.user.Address;
import com.starshipshop.starshipservice.repository.jpa.user.Email;
import com.starshipshop.starshipservice.repository.jpa.user.Telephone;
import com.starshipshop.starshipservice.repository.jpa.user.User;

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
