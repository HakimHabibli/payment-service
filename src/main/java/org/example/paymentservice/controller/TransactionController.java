package org.example.paymentservice.controller;


import org.example.paymentservice.model.entity.Transaction;
import org.example.paymentservice.service.TransactionService;
import org.hibernate.query.NativeQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController
{
    private final TransactionService _transactionService;

    public TransactionController(TransactionService transactionService) {
        _transactionService = transactionService;
    }

    @PostMapping("/create")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction)
    {
        return ResponseEntity.ok(_transactionService.createTransaction(transaction));
    }

    @PutMapping("/update")
    public  ResponseEntity<Transaction> updateTransaction(@RequestHeader Long id ,@RequestBody Transaction transaction)
    {
        return ResponseEntity.ok(_transactionService.updateTransaction(id,transaction));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Transaction> deleteTransaction(@RequestBody Transaction transaction)
    {
        return ResponseEntity.ok(_transactionService.deleteTransaction(transaction));

    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Transaction>> getAllTransactions()
    {
        return   ResponseEntity.ok(_transactionService.findAllTransactions());
    }

    @GetMapping("/getById")
    public ResponseEntity<Transaction> getTransactionById(@RequestParam Long id)
    {
        return ResponseEntity.ok(_transactionService.findTransactionById(id));
    }

}
