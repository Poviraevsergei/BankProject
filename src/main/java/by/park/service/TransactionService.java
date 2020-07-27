package by.park.service;

import by.park.controller.request.PayingTransactionRequest;
import by.park.controller.request.TransferTransactionalRequest;
import by.park.domain.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface TransactionService {

    Page<Transaction> findAllTransactions(Pageable pageable);

    Transaction findTransactionById(Long id);

    List<Transaction> transactionInformation(Principal principal);

    String paying(PayingTransactionRequest payingTransactionRequest, Principal principal);

    String transfer(TransferTransactionalRequest transferTransactionalRequest, Principal principal);

    void deleteTransactionById(Long id);
}