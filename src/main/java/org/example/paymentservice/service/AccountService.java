package org.example.paymentservice.service;

import org.example.paymentservice.model.entity.Account;
import org.example.paymentservice.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService
{
    private final AccountRepository _accountRepository;

    public AccountService(AccountRepository accountRepository)
    {
        _accountRepository = accountRepository;
    }

    public List<Account> findAll()
    {
       return _accountRepository.findAll();
    }

    public Account findById(Long id)
    {
        return _accountRepository.findById(id).orElseThrow();
    }

    public Account createAccount(Account account)
    {
        return _accountRepository.save(account);
    }

    public Account updateAccount(Long id, Account account) {

        return _accountRepository.findById(id).map(oldAccount -> {

            oldAccount.setCurrency(account.getCurrency());
            oldAccount.setBalance(account.getBalance());
            oldAccount.setTransactions(account.getTransactions());

            oldAccount.setBalance(account.getBalance());

            return _accountRepository.save(oldAccount);

        }).orElseThrow(() -> new RuntimeException("Account tapılmadı!"));
    }
    public void deleteAccount(Long id)
    {
        _accountRepository.deleteById(id);
    }
}
