package org.example.paymentservice.controller;

import org.example.paymentservice.model.dto.requestdto.Account.CreateAccountDto;
import org.example.paymentservice.model.dto.requestdto.Account.UpdateAccountDto;
import org.example.paymentservice.model.dto.responsedto.Account.GetAccountDto;
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

    @GetMapping("/getById/{id}")
    public Account getById(@PathVariable Long id)
    {
        return _accountService.findById(id);
    }

    @GetMapping("all")
    public List<GetAccountDto> getAllAccounts()
    {
        return _accountService.findAll();
    }

    @PostMapping("create")
    public ResponseEntity<CreateAccountDto> createAccount(@RequestBody CreateAccountDto account)
    {
        var accountDto = _accountService.createAccount(account);
        return ResponseEntity
                .ok(accountDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAccount(@PathVariable Long id)
    {
        _accountService.deleteAccount(id);
    }

    @PutMapping("/update/{id}")
    public UpdateAccountDto updateAccount(@RequestBody UpdateAccountDto account, @PathVariable Long id)
    {
       return  _accountService.updateAccount(id, account);
    }


}
