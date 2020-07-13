package by.park.dao;

import by.park.domain.Bank;

import java.util.List;

public interface BankDao {
    List<Bank> findAll();

    Bank findById(Long bankId);

    Bank save(Bank bank);

    Bank update(Bank bank);

    Bank delete(Long bankId);
}
