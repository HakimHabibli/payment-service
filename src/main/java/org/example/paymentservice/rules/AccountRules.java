package org.example.paymentservice.rules;

import org.example.paymentservice.exception.BusinessException;
import org.example.paymentservice.exception.NotFoundException;
import org.example.paymentservice.model.dto.requestdto.Account.UpdateAccountDto;
import org.example.paymentservice.model.entity.Account;
import org.example.paymentservice.model.enums.AccountStatus;
import org.example.paymentservice.model.enums.CurrencyType;
import org.example.paymentservice.repository.AccountRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountRules extends BaseRules<Account>
{
    private final AccountRepository accountRepository;
    public AccountRules(JpaRepository<Account, Long> repository, AccountRepository accountRepository) {
        super(repository);
        this.accountRepository = accountRepository;
    }

    public void checkIfEmployeeMaintainsSameCurrencyAccount(Long employeeId, CurrencyType currency) {

        Optional<Account> existingAccount = accountRepository.findByEmployeeIdAndCurrency(employeeId, currency);

        if (existingAccount.isPresent()) {

                throw new RuntimeException("Bu işçinin artıq " + currency + " valyutasında başqa bir hesabı mövcuddur!");

        }
    }

    @Override
    public Account findEntityIfExists(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new NotFoundException("Entity not found"));
    }

    public void checkBeforeUpdate(Account currentAccount, UpdateAccountDto updateDto) {
        if (currentAccount.getStatus() == AccountStatus.FROZEN) {
            throw new BusinessException("Frozen account cant be changed!");
        }

        if (currentAccount.getBalance() != null && currentAccount.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("Balance isn't negative");
        }
    }

    public void isAccountActive(Account currentAccount)
    {
        var account = accountRepository.findById(currentAccount.getId()).orElseThrow(()->new NotFoundException("Account not found"));
        if(!account.getStatus().equals(AccountStatus.ACTIVE)) {
            throw new BusinessException("Account isn't active!");
        }
    }
}
