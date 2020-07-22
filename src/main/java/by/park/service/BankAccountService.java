package by.park.service;

import by.park.controller.request.CreateBankAccountRequest;
import by.park.controller.request.UpdateBankAccountRequest;
import by.park.domain.BankAccount;

import java.util.List;
import java.util.Optional;

public interface BankAccountService {
    List<BankAccount> findAll();

    Optional<BankAccount> findById(Long id);

    BankAccount findBankAccountByIban(String IBAN);

    BankAccount createBankAccount(CreateBankAccountRequest createBankAccountRequest);

    BankAccount updateBankAccount(UpdateBankAccountRequest updateBankAccountRequest);

    void deleteBankAccountById(Long id);
}
