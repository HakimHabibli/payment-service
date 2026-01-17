package org.example.paymentservice.controller;

import org.example.paymentservice.model.entity.Account;
import org.example.paymentservice.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController
{
    private final AccountService _accountService;

    public AccountController(AccountService accountService)
    {
        _accountService = accountService;
    }

    @GetMapping("/getbyid/{id}")
    public Account getbyid(@PathVariable Long id)
    {
        return _accountService.findById(id);
    }

    @GetMapping("all")
    public List<Account> getAllAccounts()
    {
        return _accountService.findAll();
    }

    @PostMapping("create")
    public ResponseEntity<Account> createAccount(@RequestBody Account account)
    {
        return ResponseEntity
                .ok()
                .body(_accountService.createAccount(account));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAccount(@PathVariable Long id)
    {
        _accountService.deleteAccount(id);
    }


}
