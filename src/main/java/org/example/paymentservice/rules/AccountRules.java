package org.example.paymentservice.rules;

import org.example.paymentservice.exception.BusinessException;
import org.example.paymentservice.exception.NotFoundException;
import org.example.paymentservice.model.dto.requestdto.Account.UpdateAccountDto;
import org.example.paymentservice.model.entity.Account;
import org.example.paymentservice.model.enums.AccountStatus;
import org.example.paymentservice.repository.AccountRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.Serializable;
import java.math.BigDecimal;

@Service
public class AccountRules extends BaseRules<Account>
{
    private final AccountRepository accountRepository;
    public AccountRules(JpaRepository<Account, Long> repository, AccountRepository accountRepository) {
        super(repository);

        this.accountRepository = accountRepository;
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


}
