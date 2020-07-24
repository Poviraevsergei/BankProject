package by.park.service.impl;

import by.park.controller.request.CreateCardRequest;
import by.park.controller.request.UpdateCardRequest;
import by.park.domain.BankAccount;
import by.park.domain.Card;
import by.park.repository.BankAccountRepository;
import by.park.repository.CardRepository;
import by.park.service.CardService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    CardRepository cardRepository;
    BankAccountRepository bankAccountRepository;


    public CardServiceImpl(CardRepository cardRepository, BankAccountRepository bankAccountRepository) {
        this.cardRepository = cardRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    @Override
    public Optional<Card> findCardById(Long id) {
        return cardRepository.findById(id);
    }

    @Override
    public Card findByCardnumber(String cardNumber) {
        return cardRepository.findByCardNumber(cardNumber);
    }

    @Override
    public Card createCard(CreateCardRequest createCardRequest) {
        Card card = new Card();
        Optional<BankAccount> bankAccount = bankAccountRepository.findById(createCardRequest.getIdBankAccount());
        card.setCardNumber(createCardRequest.getCardNumber());
        card.setIdBankAccount(bankAccount.get());
        card.setExpirationDate(new Timestamp(new Date().getTime()));
        card.setCardType(createCardRequest.getCardType());
        return cardRepository.save(card);
    }

    @Override
    public Card updateCard(UpdateCardRequest updateCardRequest) {
            Card card = new Card();
            card.setId(updateCardRequest.getId());
            Optional<BankAccount> bankAccount = bankAccountRepository.findById(updateCardRequest.getIdBankAccount());
            card.setCardNumber(updateCardRequest.getCardNumber());
            card.setIdBankAccount(bankAccount.get());
            card.setExpirationDate(cardRepository.findById(updateCardRequest.getId()).get().getExpirationDate());
            card.setCardType(updateCardRequest.getCardType());
            return cardRepository.save(card);
    }

    @Override
    public void deleteCardById(Long id) {
        cardRepository.deleteCardById(id);
    }
}
