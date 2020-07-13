package by.park.dao;

import by.park.domain.BankAccount;

import java.util.List;

public interface BankAccountDao {
    List<BankAccount> findAll();

    BankAccount findById(Long bankAccountId);

    BankAccount save(BankAccount bankAccount);

    BankAccount update(BankAccount bankAccount);

    BankAccount delete(Long bankAccountId);
}
