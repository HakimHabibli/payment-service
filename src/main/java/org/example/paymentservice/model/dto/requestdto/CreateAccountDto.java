package org.example.paymentservice.model.dto.requestdto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.example.paymentservice.model.enums.AccountStatus;

import java.math.BigDecimal;


public class CreateAccountDto
{
    private Long employeeId;
    private BigDecimal balance;
    private String currency;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;
}
