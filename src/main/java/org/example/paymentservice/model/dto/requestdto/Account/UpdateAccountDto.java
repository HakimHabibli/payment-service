package org.example.paymentservice.model.dto.requestdto.Account;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.example.paymentservice.model.entity.Transaction;
import org.example.paymentservice.model.enums.AccountStatus;
import org.example.paymentservice.model.enums.CurrencyType;

import java.math.BigDecimal;
import java.util.List;

@Data
public class UpdateAccountDto
{

    private Long employeeId;
    private BigDecimal balance;
    private CurrencyType currency;
    private List<Transaction> transactions ;

    @Enumerated(EnumType.ORDINAL)
    private AccountStatus status ;
}


