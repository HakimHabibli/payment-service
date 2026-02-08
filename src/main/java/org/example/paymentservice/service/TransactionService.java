package org.example.paymentservice.service;

import jakarta.transaction.Transactional;
import org.apache.kafka.shaded.io.opentelemetry.proto.trace.v1.Status;
import org.example.paymentservice.model.entity.Transaction;
import org.example.paymentservice.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService
{
    private final TransactionRepository _transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        _transactionRepository = transactionRepository;
    }

    @Transactional
    public Transaction createTransaction(Transaction transaction)
    {
        return _transactionRepository.save(transaction);
    }

    public Transaction deleteTransaction(Transaction transaction){
        _transactionRepository.delete(transaction);
        return transaction;
    }

    public Transaction updateTransaction(Long id, Transaction request) {

        return _transactionRepository.findById(id).map(existingTransaction -> {

            existingTransaction.setStatus(request.getStatus());
            existingTransaction.setDescription(request.getDescription());

            existingTransaction.setTransactionType(request.getTransactionType());

            return _transactionRepository.save(existingTransaction);

        }).orElseThrow(() -> new RuntimeException("Not found transaction: " + id));
    }

    public Transaction findTransactionById(Long id) {
        return _transactionRepository.findById(id).orElse(null);

    }

    public List<Transaction> findAllTransactions()
    {
        return _transactionRepository.findAll();
    }

}
