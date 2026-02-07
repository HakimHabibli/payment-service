package org.example.paymentservice.converter.Account;

import org.example.paymentservice.model.dto.requestdto.Account.CreateAccountDto;
import org.example.paymentservice.model.dto.requestdto.Account.UpdateAccountDto;
import org.example.paymentservice.model.dto.responsedto.Account.GetAccountDto;
import org.example.paymentservice.model.entity.Account;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;


@Component
public class AccountMapper
{
    /**
     * Converts an Account entity to a UpdateAccountDto.
     * Used typically after a successful account creation to return the result.
     * @param account The Account entity to be converted
     * @return A UpdateAccountDto containing the basic account details
     */
    public UpdateAccountDto accountEntityToUpdateAccountDto(Account account)
    {
        UpdateAccountDto updateAccountDto = new UpdateAccountDto();
        updateAccountDto.setBalance(account.getBalance());
        updateAccountDto.setCurrency(account.getCurrency());
        updateAccountDto.setStatus(account.getStatus());
        updateAccountDto.setTransactions(account.getTransactions());
        updateAccountDto.setEmployeeId(account.getEmployeeId());
        return updateAccountDto;
    }

    /**
     * Converts an Account entity to a CreateAccountDto.
     * Used typically after a successful account creation to return the result.
     *
     * @param account The Account entity to be converted.
     * @return A CreateAccountDto containing the basic account details.
     *
     */
    public CreateAccountDto accountEntityToCreateAccountDto(Account account) {
        if(account == null) return null;

        CreateAccountDto createAccountDto = new CreateAccountDto();
        createAccountDto.setBalance(account.getBalance());
        createAccountDto.setStatus(account.getStatus());
        createAccountDto.setCurrency(account.getCurrency());
        createAccountDto.setEmployeeId(account.getEmployeeId());

        return createAccountDto;

    }

    /**
     * Maps an Account entity to a detailed GetAccountDto.
     * Includes transaction history and other full account details.
     *
     * @param account The Account entity to be mapped.
     * @return A GetAccountDto or null if the input account is null.
     */
    public  GetAccountDto getAccountToDto(Account account) {
        if (account == null) return null;

        GetAccountDto dto = new GetAccountDto();
        dto.setEmployeeId(account.getEmployeeId());
        dto.setBalance(account.getBalance());
        dto.setCurrency(account.getCurrency());
        dto.setStatus(account.getStatus());
        dto.setTransactions(account.getTransactions());

        return dto;
    }

    /**
     * Converts a list of Account entities into a list of GetAccountDtos.
     * Uses Java Stream API for the transformation process.
     *
     * @param accounts List of Account entities.
     * @return A list of mapped GetAccountDtos.
     * @throws NullPointerException if the accounts list is null.
     */
    public List<GetAccountDto> getAccountToDtoList(List<Account> accounts) {
        if (accounts == null) return null;

        return accounts.stream()
                .map(this::getAccountToDto)
                .toList();
    }

    /**
     * Maps a GetAccountDto to an Account entity using the Builder pattern.
     *
     * @param dto The data transfer object containing account details.
     * @return A new Account entity instance or null if the input dto is null.
     */
    public  Account getAccountDtoToEntity(GetAccountDto dto) {
        if (dto == null) return null;

        return Account.builder()
                .balance(dto.getBalance())
                .currency(dto.getCurrency())
                .transactions(dto.getTransactions())
                .status(dto.getStatus())
                .employeeId(dto.getEmployeeId())
                .build();
    }

    /**
     * Converts a list of GetAccountDtos to a list of Account entities.
     * Safe for null inputs; returns an empty list instead of throwing an exception.
     *
     * @param dtos List of GetAccountDtos to be mapped.
     * @return A list of Account entities or an empty list if dtos is null.
     */
    public List<Account> getAccountDtosToEntities(List<GetAccountDto> dtos) {
        if (dtos == null) return Collections.emptyList();

        return dtos.stream()
                .map(this::getAccountDtoToEntity)
                .toList();
    }

    /**
     * Converts a CreateAccountDto to an Account entity for initial creation.
     * Focuses on the core fields required for the database.
     *
     * @param dto The CreateAccountDto from the client request.
     * @return An Account entity prepared for persistence.
     * @throws NullPointerException if the dto is null.
     */
    public Account createAccountDtoToEntity(CreateAccountDto dto) {
        if(dto == null) return null;

        return Account.builder()
                .balance(dto.getBalance())
                .currency(dto.getCurrency())
                .employeeId(dto.getEmployeeId())
                .status(dto.getStatus())
                .build();

    }
}
