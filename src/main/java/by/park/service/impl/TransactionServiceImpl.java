package by.park.service.impl;

import by.park.controller.request.CreateTransactionRequest;
import by.park.domain.BankAccount;
import by.park.domain.Transaction;
import by.park.repository.BankAccountRepository;
import by.park.repository.CardRepository;
import by.park.repository.TransactionRepository;
import by.park.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Timestamp;
import java.util.Date;
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
    public String paying(@RequestBody CreateTransactionRequest createTransactionRequest) {
        BankAccount bankAccount = bankAccountRepository.findBankAccountByIban(createTransactionRequest.getIBANBankAccount());
        if (bankAccount.getAmount() >= createTransactionRequest.getCount()) {
            Transaction transaction = new Transaction();
            transaction.setTypeOfTransaction(createTransactionRequest.getTypeOfTransaction());
            transaction.setTransactionTime(new Timestamp(new Date().getTime()));
            transaction.setCount(createTransactionRequest.getCount());
            transaction.setIdBankAccount(bankAccountRepository.findBankAccountByIban(createTransactionRequest.getIBANBankAccount()));
            bankAccount.setAmount(bankAccount.getAmount() - createTransactionRequest.getCount());
            transactionRepository.save(transaction);
            return "Payment completed successfully";
        }
        return "There was a problem with payment";

    }

    @Override
    public String transfer(long count, String fromIBANBankAccount, String toIBANBankAccount) {
        if (bankAccountRepository.findBankAccountByIban(fromIBANBankAccount).getAmount() >= count) {
            Transaction transaction = new Transaction();
            bankAccountRepository.findBankAccountByIban(fromIBANBankAccount).setAmount(
                    bankAccountRepository.findBankAccountByIban(fromIBANBankAccount).getAmount() - count
            );
            bankAccountRepository.findBankAccountByIban(toIBANBankAccount).setAmount(
                    bankAccountRepository.findBankAccountByIban(toIBANBankAccount).getAmount() + count
            );
            transaction.setTypeOfTransaction("transfer money from " + fromIBANBankAccount + " to " + toIBANBankAccount);
            transaction.setCount(count);
            transaction.setIdBankAccount(bankAccountRepository.findBankAccountByIban(fromIBANBankAccount));
            transaction.setTransactionTime(new Timestamp(new Date().getTime()));
            transactionRepository.save(transaction);
            return "Money transfer was successful!";
        }
        return "There was a problem with money transfer";
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
