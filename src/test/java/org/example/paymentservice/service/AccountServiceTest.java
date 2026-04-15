package org.example.paymentservice.service;

import org.example.paymentservice.converter.Account.AccountMapper;
import org.example.paymentservice.model.dto.requestdto.Account.CreateAccountDto;
import org.example.paymentservice.model.dto.responsedto.Account.GetAccountDto;
import org.example.paymentservice.model.entity.Account;
import org.example.paymentservice.model.enums.CurrencyType;
import org.example.paymentservice.repository.AccountRepository;
import org.example.paymentservice.rules.AccountRules;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository  accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private AccountRules accountRules;

    @InjectMocks
    private AccountService accountService;

    @Test
    void findAll_shouldReturnDtoList() {
        List<Account> accounts = List.of(new Account());

        when(accountRepository.findAll()).thenReturn(accounts);
        when(accountMapper.getAccountToDtoList(accounts))
                .thenReturn(List.of(new GetAccountDto()));

        List<GetAccountDto> result = accountService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(accountRepository).findAll();
        verify(accountMapper).getAccountToDtoList(accounts);
    }

    @Test
    void findById_shouldReturnAccount() {
        Account account = new Account();
        account.setId(1L);
        when(accountRules.findEntityIfExists(1L))
            .thenReturn(account);

        Account result = accountService.findById(1L);

        assertNotNull(result);
        assertEquals(account, result);

        verify(accountRules).findEntityIfExists(1L);
    }

    @Test
    void createAccount_shouldThrowException_whenDuplicateCurrency() {

        CreateAccountDto dto = new CreateAccountDto();
        dto.setEmployeeId(1L);
        dto.setCurrency(CurrencyType.Dollar);

        Account entity = new Account();

        when(accountMapper.createAccountDtoToEntity(dto)).thenReturn(entity);

        doThrow(new RuntimeException("Duplicate account!"))
                .when(accountRules)
                .checkIfEmployeeMaintainsSameCurrencyAccount(1L, CurrencyType.Dollar);

        assertThrows(RuntimeException.class, () -> {
            accountService.createAccount(dto);
        });

        verify(accountRules).checkIfEmployeeMaintainsSameCurrencyAccount(1L, CurrencyType.Dollar);
    }
}
