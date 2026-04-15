package org.example.paymentservice.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.paymentservice.model.enums.AmountStatus;
import org.example.paymentservice.model.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name="user_transactions")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long fromAccountId;
    private Long toAccountId;
    private BigDecimal amount;


    @Enumerated(EnumType.STRING)
    private AmountStatus status;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;


    private String referenceNumber ;
    private String description ;

    private LocalDateTime transactionDate;


    /*
    * Relations
    * */


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;


}
