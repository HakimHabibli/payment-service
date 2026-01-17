package org.example.paymentservice.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.paymentservice.model.enums.AccountStatus;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
public class Account
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private Long employeeId ;
    private BigDecimal balance ;
    private String currency ;

    @Enumerated(EnumType.ORDINAL)
    private AccountStatus status ;


    //RelationField

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Transaction> transactions ;

}


