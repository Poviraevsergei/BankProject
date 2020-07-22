package by.park.controller;

import by.park.controller.request.CreateTransactionRequest;
import by.park.domain.Transaction;
import by.park.service.TransactionService;
import org.springframework.web.bind.annotation.*;

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
    String paying(CreateTransactionRequest createTransactionRequest) {
        return transactionService.paying(createTransactionRequest);
    }

    @PutMapping
    String transfer(int count, String fromIBANBankAccount, String toIBANBankAccount) {
        return transactionService.transfer(count, fromIBANBankAccount, toIBANBankAccount);
    }

    @DeleteMapping
    void deleteTransactionById(Long id) {
        transactionService.deleteTransactionById(id);
    }
}
