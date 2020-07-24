package by.park.service.impl;

import by.park.controller.request.PayingTransactionRequest;
import by.park.controller.request.TransferTransactionalRequest;
import by.park.domain.BankAccount;
import by.park.domain.Transaction;
import by.park.repository.BankAccountRepository;
import by.park.repository.CardRepository;
import by.park.repository.TransactionRepository;
import by.park.service.TransactionService;
import org.springframework.stereotype.Service;

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
    public String paying(PayingTransactionRequest payingTransactionRequest) {
        Optional<BankAccount> bankAccount = bankAccountRepository.findById(
                cardRepository.findByCardNumber(payingTransactionRequest.getCardNumber()).getIdBankAccount().getId()
        );
        if (bankAccount.get().getAmount() >= payingTransactionRequest.getCount()) {
            Transaction transaction = new Transaction();
            transaction.setTypeOfTransaction(payingTransactionRequest.getTypeOfTransaction());
            transaction.setTransactionTime(new Timestamp(new Date().getTime()));
            transaction.setCount(payingTransactionRequest.getCount());
            transaction.setIdBankAccount(bankAccount.get());
            bankAccount.get().setAmount(bankAccount.get().getAmount() - payingTransactionRequest.getCount());
            transactionRepository.save(transaction);
            return "Payment completed successfully";
        }
        return "There was a problem with payment";

    }

    @Override
    public String transfer(TransferTransactionalRequest transfer) {
        Optional<BankAccount> bankAccountFrom = bankAccountRepository.findById(cardRepository.findByCardNumber(transfer.getFromCardNumber()).getIdBankAccount().getId());
        Optional<BankAccount> bankAccountTo = bankAccountRepository.findById(cardRepository.findByCardNumber(transfer.getToCardNumber()).getIdBankAccount().getId());
        if (bankAccountFrom.get().getAmount() >= transfer.getCount()) {
            Transaction transaction = new Transaction();
            bankAccountFrom.get().setAmount(
                    bankAccountFrom.get().getAmount() - transfer.getCount());
            bankAccountTo.get().setAmount(
                    bankAccountTo.get().getAmount() + transfer.getCount());
            transaction.setTypeOfTransaction("transfer money from " + transfer.getFromCardNumber() + " to " + transfer.getToCardNumber());
            transaction.setCount(transfer.getCount());
            transaction.setIdBankAccount(bankAccountFrom.get());
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
