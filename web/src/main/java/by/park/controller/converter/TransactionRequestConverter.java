package by.park.controller.converter;

import by.park.controller.request.PayingTransactionRequest;
import by.park.domain.BankAccount;
import by.park.domain.Transaction;
import by.park.repository.BankAccountRepository;
import by.park.repository.CardRepository;

import java.sql.Timestamp;
import java.util.Date;

public abstract class TransactionRequestConverter<S, T> extends EntityConverter<S, T> {
    BankAccountRepository bankAccountRepository;
    CardRepository cardRepository;

    public TransactionRequestConverter(CardRepository cardRepository, BankAccountRepository bankAccountRepository) {
        this.cardRepository = cardRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    protected Transaction doConvert(Transaction transaction, PayingTransactionRequest request) {
        transaction.setCount(request.getCount());
        transaction.setCardNumber(request.getFromCardNumber());
        transaction.setTransactionTime(new Timestamp(new Date().getTime()));
        BankAccount bankAccount = bankAccountRepository.findById(
                cardRepository.findByCardNumber(request.getFromCardNumber()).getIdBankAccount().getId()
        ).get();
        transaction.setIdBankAccount(bankAccount);
        return transaction;
    }
}