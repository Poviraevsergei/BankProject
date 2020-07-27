package by.park.service;

import by.park.controller.request.CreateBankAccountRequest;
import by.park.controller.request.UpdateBankAccountRequest;
import by.park.domain.BankAccount;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface BankAccountService {
    List<BankAccount> findAll();

    BankAccount findById(Long id);

    BankAccount findBankAccountByIban(String IBAN);

    List<BankAccount> bankAccountInformation(Principal principal);

    BankAccount createBankAccount(CreateBankAccountRequest createBankAccountRequest);

    BankAccount updateBankAccount(UpdateBankAccountRequest updateBankAccountRequest);

    void deleteBankAccountById(Long id);
}
