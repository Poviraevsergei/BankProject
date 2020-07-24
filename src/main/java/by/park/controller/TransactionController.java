package by.park.controller;

import by.park.controller.request.PayingTransactionRequest;
import by.park.controller.request.TransferTransactionalRequest;
import by.park.domain.Transaction;
import by.park.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    TransactionService transactionService;


    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    List<Transaction> findAllTransactions() {
        return transactionService.findAllTransactions();
    }

    @GetMapping("/{id}")
    Optional<Transaction> findTransactionById(@PathVariable("id") Long id) {
        return transactionService.findTransactionById(id);
    }

    @PostMapping
    String paying(@Valid @RequestBody PayingTransactionRequest payingTransactionRequest) {
        return transactionService.paying(payingTransactionRequest);
    }

    @PutMapping
    String transfer(@Valid @RequestBody TransferTransactionalRequest transferTransactionalRequest) {
        return transactionService.transfer(transferTransactionalRequest);
    }

    @DeleteMapping
    void deleteTransactionById(Long id) {
        transactionService.deleteTransactionById(id);
    }
}
