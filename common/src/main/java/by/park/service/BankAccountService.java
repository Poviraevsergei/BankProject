package by.park.service;

import by.park.domain.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;

public interface BankAccountService {
    Page<BankAccount> findAll(Pageable pageable);

    BankAccount findById(Long id);

    BankAccount findBankAccountByIban(String IBAN);

    List<BankAccount> bankAccountInformation(Principal principal);

    BankAccount createBankAccount(BankAccount bankAccount);

    BankAccount updateBankAccount(BankAccount bankAccount);

    void deleteBankAccountById(Long id);
}