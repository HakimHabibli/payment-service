package org.example.paymentservice.service;

import org.example.paymentservice.converter.Account.AccountMapper;
import org.example.paymentservice.exception.NotFoundException;
import org.example.paymentservice.model.dto.requestdto.Account.CreateAccountDto;
import org.example.paymentservice.model.dto.requestdto.Account.UpdateAccountDto;
import org.example.paymentservice.model.dto.responsedto.Account.GetAccountDto;
import org.example.paymentservice.model.dto.responsedto.Account.GetAllAccountDto;
import org.example.paymentservice.model.entity.Account;
import org.example.paymentservice.repository.AccountRepository;
import org.example.paymentservice.rules.AccountRules;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService
{
    private final AccountRepository _accountRepository;
    private final AccountMapper _accountMapper;
    private final AccountRules _accountRules;

    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper, AccountRules accountRules) {
        _accountRepository = accountRepository;
        _accountMapper = accountMapper;
        _accountRules = accountRules;
    }

    public List<GetAccountDto> findAll() {
        List<Account> accounts = _accountRepository.findAll();
        return _accountMapper.getAccountToDtoList(accounts);
    }

    public Account findById(Long id)
    {
        return _accountRules.findEntityIfExists(id);
    }

    public CreateAccountDto createAccount(CreateAccountDto accountDto) {
        var acc =_accountMapper.createAccountDtoToEntity(accountDto);
        _accountRules.checkNotNull(acc);
        var savedEntity = _accountRepository.save(acc);
        return _accountMapper.accountEntityToCreateAccountDto(savedEntity);
    }

    public UpdateAccountDto updateAccount(Long id, UpdateAccountDto updateDto) {

        var account =_accountRules.findEntityIfExists(id);

        _accountRules.checkBeforeUpdate(account, updateDto);

        _accountMapper.accountToUpdateAccountDto(updateDto, account);

        Account savedAccount = _accountRepository.save(account);
        return _accountMapper.accountEntityToUpdateAccountDto(savedAccount);
    }

    public void deleteAccount(Long id)
    {
        _accountRepository.deleteById(id);
    }
}
