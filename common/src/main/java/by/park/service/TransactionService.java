package by.park.service;

import by.park.domain.Card;
import by.park.domain.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;

public interface TransactionService {

    Page<Transaction> findAllTransactions(Pageable pageable);

    Transaction findTransactionById(Long id);

    List<Transaction> transactionInformation(Principal principal);

    String paying(Transaction transaction, Card cardFrom, Principal principal);

    String transfer(Transaction transaction, Card cardFrom, Card cardTo, Principal principal);

    void deleteTransactionById(Long id);
}