package by.park.controller.converter;

import by.park.controller.request.CreateCardRequest;
import by.park.domain.BankAccount;
import by.park.domain.Card;
import by.park.repository.BankAccountRepository;

import java.sql.Timestamp;
import java.util.Date;

public abstract class CardRequestConverter<S, T> extends EntityConverter<S, T> {
    BankAccountRepository bankAccountRepository;

    public CardRequestConverter(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    protected Card doConvert(Card card, CreateCardRequest request) {
        card.setCardType(request.getCardType());
        BankAccount bankAccount = bankAccountRepository.findById(request.getIdBankAccount()).get();
        card.setIdBankAccount(bankAccount);
        card.setChanged(new Timestamp(new Date().getTime()));
        return card;
    }
}