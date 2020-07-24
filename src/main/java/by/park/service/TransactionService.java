package by.park.service;

import by.park.controller.request.PayingTransactionRequest;
import by.park.controller.request.TransferTransactionalRequest;
import by.park.domain.Transaction;


import java.util.List;
import java.util.Optional;

public interface TransactionService {

    List<Transaction> findAllTransactions();

    Optional<Transaction> findTransactionById(Long id);

    String paying(PayingTransactionRequest payingTransactionRequest);

    String transfer(TransferTransactionalRequest transferTransactionalRequest);

    void deleteTransactionById(Long id);
}