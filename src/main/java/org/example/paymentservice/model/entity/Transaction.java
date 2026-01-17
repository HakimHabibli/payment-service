package org.example.paymentservice.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.paymentservice.model.enums.AmountStatus;
import org.example.paymentservice.model.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name="user_transactions")
public class Transaction
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromAccountId;
    private String toAccountId;
    private BigDecimal amount;


    @Enumerated(EnumType.ORDINAL)
    private AmountStatus status;

    @Enumerated(EnumType.ORDINAL)
    private TransactionType transactionType;

    private String referenceNumber ;
    private String description ;

    private LocalDateTime transactionDate;


    /*
    * Relations
    * */


    @ManyToOne(cascade = CascadeType.ALL)
    private Account account;


}
