package by.park.dao;

import by.park.domain.Transaction;

import java.util.List;

public interface TransactionDao {
    List<Transaction> findAll();

    Transaction findById(Long transactionId);

    Transaction save(Transaction transaction);

    Transaction update(Transaction transaction);

    Transaction delete(Long transactionId);
}
