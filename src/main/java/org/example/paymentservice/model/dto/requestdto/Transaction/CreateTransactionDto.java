package org.example.paymentservice.model.dto.requestdto.Transaction;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.example.paymentservice.model.enums.AmountStatus;
import org.example.paymentservice.model.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CreateTransactionDto {

    private Long fromAccountId;
    private Long toAccountId;
    private BigDecimal amount;


    @Enumerated(EnumType.STRING)
    private AmountStatus status;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private String referenceNumber ;
    private String description ;

    private LocalDateTime transactionDate = LocalDateTime.now() ;

}
