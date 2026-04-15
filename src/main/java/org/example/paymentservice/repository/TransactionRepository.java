package org.example.paymentservice.repository;

import org.example.paymentservice.model.entity.Transaction;
import org.example.paymentservice.model.enums.AmountStatus;
import org.example.paymentservice.model.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long>
{
    List<Transaction> findByReferenceNumber(String referenceNumber);

    List<Transaction> findByAccount_Id(Long accountId);

    List<Transaction> findByStatus(AmountStatus status);

    List<Transaction> findByAccount_IdAndStatus(Long accountId, AmountStatus status);

    List<Transaction> findByTransactionDateBetween(LocalDateTime start, LocalDateTime end);

    List<Transaction> findByAccount_IdAndTransactionDateBetween(
            Long accountId, LocalDateTime start, LocalDateTime end);

    List<Transaction> findByTransactionType(TransactionType type);

    List<Transaction> findByAccount_IdAndTransactionType(Long accountId, TransactionType type);

}
