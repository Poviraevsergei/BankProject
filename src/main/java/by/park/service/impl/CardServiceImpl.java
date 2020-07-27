package by.park.service.impl;

import by.park.controller.request.CreateCardRequest;
import by.park.controller.request.UpdateCardRequest;
import by.park.domain.*;
import by.park.repository.BankAccountRepository;
import by.park.repository.CardRepository;
import by.park.repository.UserRepository;
import by.park.security.util.PrincipalUtil;
import by.park.service.CardService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {

    CardRepository cardRepository;
    BankAccountRepository bankAccountRepository;
    UserRepository userRepository;
    ConversionService conversionService;

    public CardServiceImpl(ConversionService conversionService, UserRepository userRepository, CardRepository cardRepository, BankAccountRepository bankAccountRepository) {
        this.cardRepository = cardRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
        this.conversionService = conversionService;
    }

    @Override
    public Page<Card> findAll(Pageable pageable) {
        return cardRepository.findAll(pageable);
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
        Set<BankAccount> bankAccount = user.getBankAccounts();
        Set<Card> userCards = bankAccount.stream().map(BankAccount::getCards).findAny().get();
        Set<Long> usersId = userCards.stream().map(Card::getId).collect(Collectors.toSet());
        if (!user.getDeleted()) {
            return cardRepository.findAllById(usersId);
        }
        return null;
    }

    @Override
    public Card createCard(CreateCardRequest request, Principal principal) {
        User user = userRepository.findByLogin(PrincipalUtil.getUsername(principal));
        if (user.getBankAccounts().contains(bankAccountRepository.findById(request.getIdBankAccount()).get())) {
            Card card = conversionService.convert(request, Card.class);
            return cardRepository.save(card);
        }
        return null;
    }

    @Override
    public Card updateCard(UpdateCardRequest request) {
        Card card = conversionService.convert(request, Card.class);
        return cardRepository.save(card);
    }

    @Override
    public Card blockedCard(String cardNumber, Principal principal) {
        User user = userRepository.findByLogin(PrincipalUtil.getUsername(principal));
        Card card = cardRepository.findByCardNumber(cardNumber);
        BankAccount bankAccount = card.getIdBankAccount();
        if (user.getId() == bankAccount.getUserId().getId()) {
            card.setBlocked(true);
            return cardRepository.save(card);
        }
        return card;
    }

    @Override
    public void deleteCardById(Long id) {
        cardRepository.deleteCardById(id);
    }
}
