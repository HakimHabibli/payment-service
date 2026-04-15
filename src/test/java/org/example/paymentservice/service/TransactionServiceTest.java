package org.example.paymentservice.service;

import org.example.paymentservice.converter.Account.AccountMapper;
import org.example.paymentservice.model.entity.Transaction;
import org.example.paymentservice.repository.AccountRepository;
import org.example.paymentservice.repository.TransactionRepository;
import org.example.paymentservice.rules.AccountRules;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class TransactionServiceTest
{
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountRules accountRules;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;


    @Test
    public void findAllTransactions_shouldReturnTransactions()
    {
        List<Transaction> transactions = new ArrayList<>();

        when(transactionRepository.findAll()).thenReturn(transactions);


    }
}
