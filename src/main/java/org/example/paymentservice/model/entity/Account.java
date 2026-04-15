package org.example.paymentservice.model.entity;


import jakarta.persistence.*;
import lombok.*;
import org.example.paymentservice.model.enums.AccountStatus;
import org.example.paymentservice.model.enums.CurrencyType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(name = "employee_id")
    private Long employeeId ;

    @Column(name = "balance")
    private BigDecimal balance =BigDecimal.ZERO;

    @Column(name = "currency")
    private CurrencyType currency ;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AccountStatus status ;

    //RelationField

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();
}


