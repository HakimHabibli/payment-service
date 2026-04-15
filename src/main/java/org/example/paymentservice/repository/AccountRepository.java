package org.example.paymentservice.repository;

import org.example.paymentservice.model.entity.Account;
import org.example.paymentservice.model.enums.CurrencyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmployeeIdAndCurrency(Long employeeId, CurrencyType currency);
}
