package com.devsu.hackerearth.backend.account.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.exceptions.InsufficientFoundsException;
import com.devsu.hackerearth.backend.account.exceptions.ResourceNotFoundException;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.AccountReportDto;
import com.devsu.hackerearth.backend.account.model.dto.ReportDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionReportDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper mapper;

    public TransactionServiceImpl(
            TransactionRepository transactionRepository,
            AccountRepository accountRepository,
            ModelMapper mapper) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.mapper = mapper;
    }

    @Override
    public List<TransactionDto> getAll() {
        List<TransactionDto> transactions = this.transactionRepository.findAll()
                .stream()
                .map(transaction -> mapper.map(transaction, TransactionDto.class))
                .collect(Collectors.toList());

        return transactions;
    }

    @Override
    public TransactionDto getById(Long id) {
        Transaction transaction = this.transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro una transacción con id " + id));
        return this.mapper.map(transaction, TransactionDto.class);
    }

    @Override
    public TransactionDto create(TransactionDto transactionDto) {
        Account account = this.accountRepository.findById(transactionDto.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontro una cuenta con el id " + transactionDto.getAccountId()));

        BigDecimal newBalance = account.getBalance().add(transactionDto.getAmount());
        if (newBalance.compareTo(BigDecimal.ZERO) < 0)
            throw new InsufficientFoundsException();

        account.setBalance(newBalance);
        this.accountRepository.save(account);

        Transaction transaction = mapper.map(transactionDto, Transaction.class);
        transaction.setId(null);
        transaction.setBalance(newBalance);

        Transaction transactionSaved = this.transactionRepository.save(transaction);
        return this.mapper.map(transactionSaved, TransactionDto.class);
    }

    @Override
    public ReportDto getAllByAccountClientIdAndDateBetween(
            Long clientId,
            LocalDate dateTransactionStart,
            LocalDate dateTransactionEnd) {
        List<Account> accounts = this.accountRepository.findByClientId(clientId);
        if (accounts.isEmpty())
            throw new ResourceNotFoundException("El cliente no poseé cuentas");

        ReportDto report = new ReportDto();
        report.setClientId(clientId);
        report.setStartAt(dateTransactionStart);
        report.setEndAt(dateTransactionEnd);

        report.setAccounts(accounts.stream().map(account -> {
            AccountReportDto accountReportDto = new AccountReportDto();
            accountReportDto.setId(account.getId());
            accountReportDto.setNumber(account.getNumber());

            List<Transaction> transactions = this.transactionRepository
                    .findByAccountIdAndDateBetweenOrderByIdDesc(
                            account.getId(), dateTransactionStart, dateTransactionEnd);

            BigDecimal inital = account.getInitialAmount().add(
                    transactions.stream().map(Transaction::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
            accountReportDto.setInitialAmount(inital);

            BigDecimal balance = transactions.stream()
                    .map(Transaction::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            accountReportDto.setBalance(balance);

            accountReportDto.setTransactions(transactions.stream().map(transaction -> {
                TransactionReportDto transactionReportDto = new TransactionReportDto();
                transactionReportDto.setId(transaction.getId());
                transactionReportDto.setDate(transaction.getDate());
                transactionReportDto.setType(transaction.getType());
                transactionReportDto.setAmount(transaction.getAmount());
                transactionReportDto.setBalance(transaction.getBalance());

                return transactionReportDto;
            }).collect(Collectors.toList()));

            return accountReportDto;
        }).collect(Collectors.toList()));

        return report;
    }

    @Override
    public TransactionDto getLastByAccountId(Long accountId) {
        // If you need it
        return null;
    }

}
