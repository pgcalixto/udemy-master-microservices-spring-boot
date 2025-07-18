package com.pgcalixto.calixtobank.accounts.service.impl;

import com.pgcalixto.calixtobank.accounts.constants.AccountConstants;
import com.pgcalixto.calixtobank.accounts.dto.AccountDto;
import com.pgcalixto.calixtobank.accounts.dto.CustomerDto;
import com.pgcalixto.calixtobank.accounts.entity.Account;
import com.pgcalixto.calixtobank.accounts.entity.Customer;
import com.pgcalixto.calixtobank.accounts.exception.CustomerAlreadyExistsException;
import com.pgcalixto.calixtobank.accounts.exception.ResourceNotFoundException;
import com.pgcalixto.calixtobank.accounts.mapper.AccountMapper;
import com.pgcalixto.calixtobank.accounts.mapper.CustomerMapper;
import com.pgcalixto.calixtobank.accounts.repository.AccountRepository;
import com.pgcalixto.calixtobank.accounts.repository.CustomerRepository;
import com.pgcalixto.calixtobank.accounts.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private static final Random RANDOM = new Random();

    private AccountMapper accountMapper;

    private CustomerMapper customerMapper;

    private AccountRepository accountRepository;

    private CustomerRepository customerRepository;

    /**
     * @param customerDto - CustomerDto Object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {

        final Optional<Customer> optionalCustomer = customerRepository
                .findByMobileNumber(customerDto.getMobileNumber());

        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                    + customerDto.getMobileNumber());
        }

        final Customer customer = customerMapper.mapToCustomer(customerDto);

        final Customer savedCustomer = customerRepository.save(customer);

        final Account newAccount = createNewAccount(savedCustomer);

        accountRepository.save(newAccount);
    }

    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Account createNewAccount(Customer customer) {

        final long randomAccountNumber = 1000000000L + RANDOM.nextInt(900000000);

        final Account newAccount = new Account();
        newAccount.setCustomerId(customer.getCustomerId());
        newAccount.setAccountNumber(randomAccountNumber);
        newAccount.setAccountType(AccountConstants.SAVINGS);
        newAccount.setBranchAddress(AccountConstants.ADDRESS);

        return newAccount;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Account Details based on a given mobileNumber
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {

        final Customer customer = customerRepository
                .findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        AccountConstants.CUSTOMER,
                        "mobileNumber",
                        mobileNumber));

        final Account account = accountRepository
                .findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Account",
                        "customerId",
                        customer.getCustomerId().toString()));

        return customerMapper.mapToCustomerDto(customer, account);
    }

    /**
     * @param customerDto - CustomerDto Object
     * @return boolean indicating if the update of Account details is successful or
     *         not
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {

        final AccountDto accountDto = customerDto.getAccountDto();

        if (accountDto == null) {
            return false;
        }

        final Account account = accountRepository
                .findById(accountDto.getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Account",
                        "AccountNumber",
                        accountDto.getAccountNumber().toString()));

        final Long customerId = account.getCustomerId();

        final Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        AccountConstants.CUSTOMER,
                        "CustomerID",
                        customerId.toString()));

        accountMapper.updateAccounts(accountDto, account);

        accountRepository.save(account);

        customerMapper.updateCustomer(customerDto, customer);

        customerRepository.save(customer);

        return true;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of Account details is successful or
     *         not
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {

        final Customer customer = customerRepository
                .findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        AccountConstants.CUSTOMER,
                        "mobileNumber",
                        mobileNumber));

        accountRepository.deleteByCustomerId(customer.getCustomerId());

        customerRepository.deleteById(customer.getCustomerId());

        return true;
    }

}
