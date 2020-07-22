package by.park.service;

import by.park.controller.request.CreateTransactionRequest;
import by.park.domain.Transaction;


import java.util.List;
import java.util.Optional;

public interface TransactionService {

    List<Transaction> findAllTransactions();

    Optional<Transaction> findTransactionById(Long id);

    String paying(CreateTransactionRequest createTransactionRequest);

    String transfer(long count, String fromIBANBankAccount, String toIBANBankAccount);

    void deleteTransactionById(Long id);
}