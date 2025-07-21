package com.devsu.hackerearth.backend.account.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.exceptions.ResourceNotFoundException;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper mapper;

    public AccountServiceImpl(AccountRepository accountRepository, ModelMapper mapper) {
        this.accountRepository = accountRepository;
        this.mapper = mapper;
    }

    @Override
    public List<AccountDto> getAll() {
        List<AccountDto> accounts = this.accountRepository.findAll()
                .stream()
                .map(account -> mapper.map(account, AccountDto.class))
                .collect(Collectors.toList());

        return accounts;
    }

    @Override
    public AccountDto getById(Long id) {
        Account account = this.accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account with id " + id + " not found"));
        return this.mapper.map(account, AccountDto.class);
    }

    @Override
    public AccountDto create(AccountDto accountDto) {
        Account account = mapper.map(accountDto, Account.class);
        account.setId(null);
        Account accountSaved = this.accountRepository.save(account);
        return this.mapper.map(accountSaved, AccountDto.class);
    }

    @Override
    public AccountDto update(Long id, AccountDto accountDto) {
        Account account = this.accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account with id " + id + " not found"));

        account.setNumber(accountDto.getNumber());
        account.setType(accountDto.getType());
        account.setInitialAmount(accountDto.getInitialAmount());
        account.setBalance(accountDto.getBalance());
        account.setActive(accountDto.isActive());
        account.setClientId(accountDto.getClientId());

        Account accountSaved = this.accountRepository.save(account);
        return this.mapper.map(accountSaved, AccountDto.class);
    }

    @Override
    public AccountDto partialUpdate(Long id, PartialAccountDto partialAccountDto) {
        Account account = this.accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account with id " + id + " not found"));

        account.setActive(partialAccountDto.isActive());

        Account accountSaved = this.accountRepository.save(account);
        return this.mapper.map(accountSaved, AccountDto.class);
    }

    @Override
    public void deleteById(Long id) {
        if (!this.accountRepository.existsById(id))
            throw new ResourceNotFoundException("Account with id " + id + " not found");
    }

}
