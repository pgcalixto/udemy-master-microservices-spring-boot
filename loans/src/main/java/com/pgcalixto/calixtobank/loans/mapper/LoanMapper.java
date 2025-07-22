package com.pgcalixto.calixtobank.loans.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.pgcalixto.calixtobank.loans.dto.LoanDto;
import com.pgcalixto.calixtobank.loans.entity.Loan;

@Mapper(componentModel = ComponentModel.SPRING)
public interface LoanMapper {

    LoanDto mapToLoanDto(Loan loan);

    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "loanId", ignore = true)
    void updateLoan(LoanDto loanDto, @MappingTarget Loan loan);

}
