package by.park.service.impl;

import by.park.controller.request.CreateCardRequest;
import by.park.controller.request.UpdateCardRequest;
import by.park.domain.*;
import by.park.repository.BankAccountRepository;
import by.park.repository.CardRepository;
import by.park.repository.UserRepository;
import by.park.security.util.PrincipalUtil;
import by.park.service.CardService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static by.park.util.MethodsForCreating.createCardNumber;

@Service
public class CardServiceImpl implements CardService {

    CardRepository cardRepository;
    BankAccountRepository bankAccountRepository;
    UserRepository userRepository;

    public CardServiceImpl(UserRepository userRepository, CardRepository cardRepository, BankAccountRepository bankAccountRepository) {
        this.cardRepository = cardRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    @Override
    public Card findCardById(Long id) {
        return cardRepository.findById(id).get();
    }

    @Override
    public Card findByCardNumber(String cardNumber) {
        return cardRepository.findByCardNumber(cardNumber);
    }

    @Override
    public List<Card> cardInformation(Principal principal) {
        User user = userRepository.findByLogin(PrincipalUtil.getUsername(principal));
        Set<BankAccount> bankAccount=user.getBankAccounts();
        Set<Card> userCards =bankAccount.stream().map(BankAccount::getCards).findAny().get();
        Set<Long> usersId =userCards.stream().map(Card::getId).collect(Collectors.toSet());
        if(!user.getDeleted()){
            return cardRepository.findAllById(usersId);
        }
        return null;
    }

    @Override
    public Card createCard(CreateCardRequest createCardRequest) {
        Card card = new Card();
        BankAccount bankAccount = bankAccountRepository.findById(createCardRequest.getIdBankAccount()).get();
        card.setCardNumber(createCardNumber());
        card.setIdBankAccount(bankAccount);
        card.setExpirationDate(new Timestamp(new Date().getTime()));
        card.setCardType(createCardRequest.getCardType());
        return cardRepository.save(card);
    }

    @Override
    public Card updateCard(UpdateCardRequest updateCardRequest) {
        Card card = new Card();
        card.setId(updateCardRequest.getId());
        BankAccount bankAccount = bankAccountRepository.findById(updateCardRequest.getIdBankAccount()).get();
        card.setCardNumber(updateCardRequest.getCardNumber());
        card.setIdBankAccount(bankAccount);
        card.setExpirationDate(cardRepository.findById(updateCardRequest.getId()).get().getExpirationDate());
        card.setCardType(updateCardRequest.getCardType());
        return cardRepository.save(card);
    }

    @Override
    public void deleteCardById(Long id) {
        cardRepository.deleteCardById(id);
    }
}
