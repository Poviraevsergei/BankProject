package by.park.service;

import by.park.controller.request.CreateTransactionRequest;
import by.park.domain.Transaction;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;
import java.util.Optional;

public interface TransactionService {

    List<Transaction> findAllTransactions();

    List<Transaction> findTransactionsByType(String type);

    Transaction paying(CreateTransactionRequest createTransactionRequest);

    Transaction transfer(int count,Long fromIdBankAccount, Long toIdBankAccount);

    Optional<Transaction> findTransactionById(Long id);

    void deleteTransactionById(Long id);
}
