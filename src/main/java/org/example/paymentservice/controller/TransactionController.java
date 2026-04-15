package org.example.paymentservice.controller;


import org.example.paymentservice.model.entity.Transaction;
import org.example.paymentservice.model.enums.AmountStatus;
import org.example.paymentservice.service.TransactionService;
import org.hibernate.query.NativeQuery;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController
{
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/create")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction)
    {
        return ResponseEntity.ok(transactionService.createTransaction(transaction));
    }

    @PutMapping("/update")
    public  ResponseEntity<Transaction> updateTransaction(@RequestHeader Long id ,@RequestBody Transaction transaction)
    {
        return ResponseEntity.ok(transactionService.updateTransaction(id,transaction));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Transaction> deleteTransaction(@RequestBody Transaction transaction)
    {
        return ResponseEntity.ok(transactionService.deleteTransaction(transaction));

    }


    @PostMapping("/transfer")
    public ResponseEntity<Map<String, Object>> transferMoney(
            @RequestParam Long fromAccountId,
            @RequestParam Long toAccountId,
            @RequestParam BigDecimal amount,
            @RequestParam(required = false) String description) {

        Map<String, Object> response = transactionService.transfer(fromAccountId, toAccountId, amount, description);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{id}/reverse")
    public ResponseEntity<Transaction> reverseTransaction(
            @PathVariable Long id,
            @RequestParam String reason) {

        Transaction response = transactionService.reverseTransaction(id, reason);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/reference/{referenceNumber}")
    public ResponseEntity<List<Transaction>> getByReferenceNumber(@PathVariable String referenceNumber) {
        List<Transaction> transactions = transactionService.findByReferenceNumber(referenceNumber);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Transaction>> getAllTransactions()
    {
        return   ResponseEntity.ok(transactionService.findAllTransactions());
    }

    @GetMapping("/getById")
    public ResponseEntity<Transaction> getTransactionById(@RequestParam Long id)
    {
        return ResponseEntity.ok(transactionService.findTransactionById(id));
    }


    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Transaction>> getAccountTransactions(@PathVariable Long accountId) {
        List<Transaction> transactions = transactionService.findByAccountId(accountId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/by-date-range")
    public ResponseEntity<List<Transaction>> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(required = false) Long accountId) {

        List<Transaction> transactions = transactionService.findByDateRange(startDate, endDate, accountId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Transaction>> getByStatus(@PathVariable AmountStatus status) {
        List<Transaction> transactions = transactionService.findByStatus(status);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/account/{accountId}/status/{status}")
    public ResponseEntity<List<Transaction>> getByAccountAndStatus(
            @PathVariable Long accountId,
            @PathVariable AmountStatus status) {

        List<Transaction> transactions = transactionService.findByAccountAndStatus(accountId, status);
        return ResponseEntity.ok(transactions);
    }

}