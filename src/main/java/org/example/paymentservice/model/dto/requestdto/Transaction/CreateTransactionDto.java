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

    private String fromAccountId;
    private String toAccountId;
    private BigDecimal amount;


    @Enumerated(EnumType.ORDINAL)
    private AmountStatus status;

    @Enumerated(EnumType.ORDINAL)
    private TransactionType transactionType;

    private String referenceNumber ;
    private String description ;

    private LocalDateTime transactionDate = LocalDateTime.now() ;

}
