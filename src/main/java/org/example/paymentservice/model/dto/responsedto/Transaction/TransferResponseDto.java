package org.example.paymentservice.model.dto.responsedto.Transaction;

import lombok.*;
import org.example.paymentservice.model.enums.AmountStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferResponseDto {

    private String referenceNumber;
    private Long fromAccountId;
    private Long toAccountId;
    private BigDecimal amount;
    private BigDecimal fromAccountNewBalance;
    private BigDecimal toAccountNewBalance;
    private LocalDateTime transactionDate;
    private AmountStatus status;
}


