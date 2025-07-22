package com.pgcalixto.calixtobank.loans.service.impl;

import com.pgcalixto.calixtobank.loans.constants.LoanConstants;
import com.pgcalixto.calixtobank.loans.dto.LoanDto;
import com.pgcalixto.calixtobank.loans.entity.Loan;
import com.pgcalixto.calixtobank.loans.exception.LoanAlreadyExistsException;
import com.pgcalixto.calixtobank.loans.exception.ResourceNotFoundException;
import com.pgcalixto.calixtobank.loans.mapper.LoanMapper;
import com.pgcalixto.calixtobank.loans.repository.LoanRepository;
import com.pgcalixto.calixtobank.loans.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements LoanService {

    private static final Random RANDOM = new Random();

    private LoanMapper loanMapper;

    private LoanRepository loanRepository;

    /**
     * @param mobileNumber - Mobile Number of the Customer
     */
    @Override
    public void createLoan(String mobileNumber) {

        final Optional<Loan> optionalLoan = loanRepository.findByMobileNumber(mobileNumber);

        if (optionalLoan.isPresent()) {
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber " + mobileNumber);
        }

        loanRepository.save(createNewLoan(mobileNumber));
    }

    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new loan details
     */
    private Loan createNewLoan(String mobileNumber) {

        final long randomLoanNumber = 100000000000L + RANDOM.nextInt(900000000);

        final Loan newLoan = new Loan();
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoanConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoanConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoanConstants.NEW_LOAN_LIMIT);

        return newLoan;
    }

    /**
     *
     * @param mobileNumber - Input mobile Number
     * @return Loan Details based on a given mobileNumber
     */
    @Override
    public LoanDto fetchLoan(String mobileNumber) {

        final Loan loan = loanRepository
                .findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));

        return loanMapper.mapToLoanDto(loan);
    }

    /**
     *
     * @param loanDto - LoansDto Object
     * @return boolean indicating if the update of loan details is successful or not
     */
    @Override
    public boolean updateLoan(LoanDto loanDto) {

        final Loan loan = loanRepository
                .findByLoanNumber(loanDto.getLoanNumber())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Loan",
                        "LoanNumber",
                        loanDto.getLoanNumber()));

        loanMapper.updateLoan(loanDto, loan);

        loanRepository.save(loan);

        return true;
    }

    /**
     * @param mobileNumber - Input MobileNumber
     * @return boolean indicating if the delete of loan details is successful or not
     */
    @Override
    public boolean deleteLoan(String mobileNumber) {

        final Loan loan = loanRepository
                .findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));

        loanRepository.deleteById(loan.getLoanId());

        return true;
    }

}
