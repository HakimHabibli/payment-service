package org.example.paymentservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.shaded.io.opentelemetry.proto.trace.v1.Status;
import org.example.paymentservice.exception.NotFoundException;
import org.example.paymentservice.model.entity.Account;
import org.example.paymentservice.model.entity.Transaction;
import org.example.paymentservice.model.enums.AmountStatus;
import org.example.paymentservice.model.enums.TransactionType;
import org.example.paymentservice.repository.AccountRepository;
import org.example.paymentservice.repository.TransactionRepository;
import org.example.paymentservice.rules.AccountRules;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransactionService
{
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final AccountRules accountRules;


    @Transactional
    public Transaction createTransaction(Transaction transaction)
    {
        Account account = accountRepository.findById(transaction.getToAccountId()).orElseThrow(()-> new NotFoundException("Account not found"));

        accountRules.isAccountActive(account);

        //TODO Dto converter yazilmalidi

        transactionRepository.save(transaction);

        account.setBalance(account.getBalance().add(transaction.getAmount()));
        return transaction;
    }

    public Transaction deleteTransaction(Transaction transaction){
        transactionRepository.delete(transaction);
        return transaction;
    }

    public Transaction updateTransaction(Long id, Transaction request) {

        return transactionRepository.findById(id).map(existingTransaction -> {

            existingTransaction.setStatus(request.getStatus());
            existingTransaction.setDescription(request.getDescription());

            existingTransaction.setTransactionType(request.getTransactionType());

            return transactionRepository.save(existingTransaction);

        }).orElseThrow(() -> new RuntimeException("Not found transaction: " + id));
    }

    @Transactional
    public Map<String, Object> transfer(Long fromAccountId, Long toAccountId, BigDecimal amount, String description) {

        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new NotFoundException("From account not found: " + fromAccountId));

        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new NotFoundException("To account not found: " + toAccountId));

        accountRules.isAccountActive(fromAccount);
        accountRules.isAccountActive(toAccount);

        if (!fromAccount.getCurrency().equals(toAccount.getCurrency())) {
            throw new RuntimeException("Currency mismatch!");
        }

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance!");
        }

        // 3. Reference number (eyni olacaq)
        String refNumber = "TRF" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));

        // 4. DEBIT transaksiyası
        Transaction debit = Transaction.builder()
                .account(fromAccount)
                .fromAccountId(fromAccountId)
                .toAccountId(toAccountId)
                .amount(amount.negate()) // mənfi
                .transactionType(TransactionType.TRANSFER)
                .status(AmountStatus.SUCCESS)
                .referenceNumber(refNumber)
                .description(description != null ? description : "Transfer to #" + toAccountId)
                .transactionDate(LocalDateTime.now())
                .build();

        transactionRepository.save(debit);

        Transaction credit = Transaction.builder()
                .account(toAccount)
                .fromAccountId(fromAccountId)
                .toAccountId(toAccountId)
                .amount(amount) // müsbət
                .transactionType(TransactionType.SALARY)
                .status(AmountStatus.SUCCESS)
                .referenceNumber(refNumber)
                .description(description != null ? description : "Transfer from #" + fromAccountId)
                .transactionDate(LocalDateTime.now())
                .build();

        transactionRepository.save(credit);

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Map<String, Object> response = new HashMap<>();
        response.put("referenceNumber", refNumber);
        response.put("fromAccountId", fromAccountId);
        response.put("toAccountId", toAccountId);
        response.put("amount", amount);
        response.put("fromAccountNewBalance", fromAccount.getBalance());
        response.put("toAccountNewBalance", toAccount.getBalance());
        response.put("transactionDate", LocalDateTime.now());

        return response;
    }

    @Transactional
    public Transaction reverseTransaction(Long transactionId, String reason) {

        Transaction original = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NotFoundException("Transaction not found: " + transactionId));

        if (original.getStatus() != AmountStatus.SUCCESS) {
            throw new RuntimeException("Only SUCCESS transactions can be reversed");
        }

        Account account = original.getAccount();

        Transaction reversal = Transaction.builder()
                .account(account)
                .fromAccountId(original.getToAccountId())
                .toAccountId(original.getFromAccountId())
                .amount(original.getAmount().negate()) // əks işarə
                .transactionType(original.getTransactionType() == TransactionType.TRANSFER ?
                        TransactionType.SALARY : TransactionType.BONUS)
                .status(AmountStatus.SUCCESS)
                .referenceNumber("REV-" + original.getReferenceNumber())
                .description("Reversal: " + reason)
                .transactionDate(LocalDateTime.now())
                .build();

        transactionRepository.save(reversal);

        original.setStatus(AmountStatus.RESERVED);
        transactionRepository.save(original);


        account.setBalance(account.getBalance().add(reversal.getAmount()));
        accountRepository.save(account);

        return reversal;
    }

    public List<Transaction> findByReferenceNumber(String referenceNumber) {
        return transactionRepository.findByReferenceNumber(referenceNumber);
    }

    public List<Transaction> findByAccountId(Long accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new NotFoundException("Account not found: " + accountId);
        }
        return transactionRepository.findByAccount_Id(accountId);
    }

    public List<Transaction> findByDateRange(LocalDateTime startDate, LocalDateTime endDate, Long accountId) {
        if (accountId != null) {
            return transactionRepository.findByAccount_IdAndTransactionDateBetween(
                    accountId, startDate, endDate);
        } else {
            return transactionRepository.findByTransactionDateBetween(startDate, endDate);
        }
    }

    public Transaction findTransactionById(Long id)
    {
        return transactionRepository.findById(id).orElse(null);
    }

    public List<Transaction> findAllTransactions()
    {
        return transactionRepository.findAll();
    }

    public List<Transaction> findByStatus(AmountStatus status) {
        return transactionRepository.findByStatus(status);
    }

    public List<Transaction> findByAccountAndStatus(Long accountId, AmountStatus status) {
        return transactionRepository.findByAccount_IdAndStatus(accountId, status);
    }
}
