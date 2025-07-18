package com.pgcalixto.calixtobank.accounts.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.MappingConstants.ComponentModel;

import com.pgcalixto.calixtobank.accounts.dto.AccountDto;
import com.pgcalixto.calixtobank.accounts.entity.Account;

@Mapper(componentModel = ComponentModel.SPRING)
public interface AccountMapper {

    AccountDto mapToAccountsDto(Account account);

    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "customerId", ignore = true)
    void updateAccounts(AccountDto accountsDto, @MappingTarget Account account);

}
