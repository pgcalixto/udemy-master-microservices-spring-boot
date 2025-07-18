package com.pgcalixto.calixtobank.accounts.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.pgcalixto.calixtobank.accounts.dto.CustomerDto;
import com.pgcalixto.calixtobank.accounts.entity.Account;
import com.pgcalixto.calixtobank.accounts.entity.Customer;

@Mapper(componentModel = ComponentModel.SPRING, uses = { AccountMapper.class })
public interface CustomerMapper {

    @Mapping(target = "accountDto", source = "account")
    CustomerDto mapToCustomerDto(Customer customer, Account account);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "customerId", ignore = true)
    Customer mapToCustomer(CustomerDto customerDto);

    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "customerId", ignore = true)
    void updateCustomer(CustomerDto customerDto, @MappingTarget Customer customer);

}
