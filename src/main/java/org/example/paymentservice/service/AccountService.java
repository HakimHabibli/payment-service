package org.example.paymentservice.service;

import org.example.paymentservice.converter.Account.AccountMapper;
import org.example.paymentservice.exception.NotFoundException;
import org.example.paymentservice.model.dto.requestdto.Account.CreateAccountDto;
import org.example.paymentservice.model.dto.requestdto.Account.UpdateAccountDto;
import org.example.paymentservice.model.dto.responsedto.Account.GetAccountDto;
import org.example.paymentservice.model.dto.responsedto.Account.GetAllAccountDto;
import org.example.paymentservice.model.entity.Account;
import org.example.paymentservice.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService
{
    private final AccountRepository _accountRepository;
    private final AccountMapper _accountMapper;

    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper) {
        _accountRepository = accountRepository;
        _accountMapper = accountMapper;
    }

    public List<GetAccountDto> findAll() {
        List<Account> accounts = _accountRepository.findAll();
        return _accountMapper.getAccountToDtoList(accounts);
    }

    public Account findById(Long id)
    {
        return _accountRepository.findById(id).orElseThrow(()-> new NotFoundException("Account is not found"));
    }

    public CreateAccountDto createAccount(CreateAccountDto accountDto) {
        if(accountDto == null) throw new IllegalArgumentException();

        var acc =_accountMapper.createAccountDtoToEntity(accountDto);
        var savedEntity = _accountRepository.save(acc);
        return _accountMapper.accountEntityToCreateAccountDto(savedEntity);
    }

    public UpdateAccountDto updateAccount(Long id, UpdateAccountDto account) {

        return _accountRepository.findById(id).map(oldAccount -> {

            oldAccount.setCurrency(account.getCurrency());
            oldAccount.setBalance(account.getBalance());
            oldAccount.setTransactions(account.getTransactions());

            oldAccount.setBalance(account.getBalance());

            Account savedAccount = _accountRepository.save(oldAccount);

            return _accountMapper.accountEntityToUpdateAccountDto(savedAccount);

        }).orElseThrow(() -> new RuntimeException("Account tapılmadı!"));
    }

    public void deleteAccount(Long id)
    {
        _accountRepository.deleteById(id);
    }
}
