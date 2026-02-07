package org.example.paymentservice.model.dto.responsedto.Account;

import jakarta.persistence.*;
import lombok.Data;
import org.example.paymentservice.model.entity.Transaction;
import org.example.paymentservice.model.enums.AccountStatus;
import org.example.paymentservice.model.enums.CurrencyType;
import java.math.BigDecimal;
import java.util.List;


@Data
public class GetAccountDto
{

    private Long employeeId ;
    private BigDecimal balance ;
    private CurrencyType currency ;

    @Enumerated(EnumType.ORDINAL)
    private AccountStatus status ;

    private List<Transaction> transactions ;
}
