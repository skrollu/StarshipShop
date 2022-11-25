package com.example.starshipshop.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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

    @Mapping(target = "account", ignore = true)
    User toUser(SimpleUserDto dto);

    SimpleUserDto toSimpleUserDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "account", ignore = true)
    User fromCreateUserRequestInput(CreateUserRequestInput curi);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "account", ignore = true)
    User fromUpdateUserRequestInput(UpdateUserRequestInput uuri);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Address toAddress(AddressDto dto);
    
    AddressDto toAddressDto(Address address);
    
    @Mapping(target = "id", ignore = true)
    Address fromAddressRequestInput(AddressRequestInput ari);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Email toEmail(EmailDto dto);

    EmailDto toEmailDto(Email email);

    @Mapping(target = "id", ignore = true)
    Email fromEmailRequestInput(EmailRequestInput eri);

    @Mapping(target = "id", ignore = true) // TODO REMOVE to avoid bugs on create User may need to have
                                           // one with/out id
    @Mapping(target = "user", ignore = true)
    Telephone toTelephone(TelephoneDto dto);
    
    TelephoneDto toTelephoneDto(Telephone telephone);
    
    @Mapping(target = "id", ignore = true)
    Telephone fromTelephoneRequestInput(UpdateUserTelephoneRequestInput tri);
}
