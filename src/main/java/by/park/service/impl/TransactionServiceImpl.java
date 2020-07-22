package by.park.service.impl;

import by.park.controller.request.CreateTransactionRequest;
import by.park.domain.BankAccount;
import by.park.domain.Card;
import by.park.domain.Transaction;
import by.park.repository.BankAccountRepository;
import by.park.repository.CardRepository;
import by.park.repository.TransactionRepository;
import by.park.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    TransactionRepository transactionRepository;
    BankAccountRepository bankAccountRepository;
    CardRepository cardRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, CardRepository cardRepository, BankAccountRepository bankAccountRepository) {
        this.transactionRepository = transactionRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.cardRepository = cardRepository;

    }

    @Override
    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> findTransactionsByType(String type) {
        return transactionRepository.findAllByTypeOfTransaction(type);
    }

    @Override
    public Transaction paying(@RequestBody CreateTransactionRequest createTransactionRequest) {
        Transaction transaction = new Transaction();
        Optional<BankAccount> bankAccount = bankAccountRepository.findById(createTransactionRequest.getIdBankAccount());
        if (bankAccount.get().getAmount() >= createTransactionRequest.getCount()) {
            bankAccount.get().setAmount(bankAccount.get().getAmount() - createTransactionRequest.getCount());
        }
        transaction.setTypeOfTransaction(createTransactionRequest.getTypeOfTransaction());
        return transaction;
    }

    @Override
    public Transaction transfer(int count, Long fromIdBankAccount, Long toIdBankAccount) {
        Transaction transaction = new Transaction();
        transaction.setTypeOfTransaction("transfer from BankAccount id=" + fromIdBankAccount + " to BankAccount id=" + toIdBankAccount);
        if (bankAccountRepository.findById(fromIdBankAccount).get().getAmount() > count) {
            bankAccountRepository.findById(toIdBankAccount).get().setAmount(
                    bankAccountRepository.findById(toIdBankAccount).get().getAmount() + count
            );
            bankAccountRepository.findById(fromIdBankAccount).get().setAmount(
                    bankAccountRepository.findById(fromIdBankAccount).get().getAmount() - count
            );
        }
        return null;
    }

    @Override
    public Optional<Transaction> findTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    public void deleteTransactionById(Long id) {
        transactionRepository.deleteTransactionById(id);
    }
}
