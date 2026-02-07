package org.example.paymentservice.model.dto.requestdto.Account;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.example.paymentservice.model.enums.AccountStatus;
import org.example.paymentservice.model.enums.CurrencyType;

import java.math.BigDecimal;



@Data
public class CreateAccountDto
{
    private Long employeeId;
    private BigDecimal balance;
    private CurrencyType currency;


    @Enumerated(EnumType.STRING)
    private AccountStatus status;
}
