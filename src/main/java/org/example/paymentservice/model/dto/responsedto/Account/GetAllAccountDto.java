package org.example.paymentservice.model.dto.responsedto.Account;

import lombok.Data;

import java.util.List;


@Data
public class GetAllAccountDto
{
    private List<GetAllAccountDto> accounts ;
}
