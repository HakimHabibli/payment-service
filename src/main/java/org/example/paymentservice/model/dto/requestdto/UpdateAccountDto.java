package org.example.paymentservice.model.dto.requestdto;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.example.paymentservice.model.enums.AccountStatus;

import java.math.BigDecimal;

@Data
public class UpdateAccountDto
{
    private Long employeeId;
    private BigDecimal balance;
    private String currency;

    @Enumerated(EnumType.ORDINAL)
    private AccountStatus status ;
}
