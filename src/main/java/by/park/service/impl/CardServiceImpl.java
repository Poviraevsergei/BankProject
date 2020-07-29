package by.park.service.impl;

import by.park.controller.request.CreateCardRequest;
import by.park.controller.request.UpdateCardRequest;
import by.park.domain.BankAccount;
import by.park.domain.Card;
import by.park.domain.User;
import by.park.repository.BankAccountRepository;
import by.park.repository.CardRepository;
import by.park.repository.UserRepository;
import by.park.security.util.PrincipalUtil;
import by.park.service.CardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@CacheConfig(cacheNames = "cards")
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

    @Cacheable("cards")
    @Override
    public Page<Card> findAll(Pageable pageable) {
        Page<Card> result = cardRepository.findAll(pageable);
        if (result.getTotalElements() == 0) {
            log.warn("Method findAll: cards not found !");
        } else {
            log.info("Method findAll: cards found.");
        }
        return result;
    }

    @Override
    public Card findCardById(Long id) {
        Optional<Card> result = cardRepository.findById(id);
        if (result.isPresent()) {
            log.info("Method findCardById: card found.");
        } else {
            log.warn("Method findCardById: card not found !");
        }
        return result.get();
    }

    @Override
    public Card findByCardNumber(String cardNumber) {
        Card result = cardRepository.findByCardNumber(cardNumber);
        if (result == null) {
            log.warn("Method findByCardNumber: card not found!");
        } else {
            log.info("Method findByCardNumber: card found.");
        }
        return result;
    }

    @Override
    public List<Card> cardInformation(Principal principal) {
        User user = userRepository.findByLogin(PrincipalUtil.getUsername(principal));
        Set<BankAccount> bankAccount = user.getBankAccounts();
        Set<Card> userCards = bankAccount.stream().map(BankAccount::getCards).findAny().get();
        Set<Long> usersId = userCards.stream().map(Card::getId).collect(Collectors.toSet());
        if (!user.getDeleted()) {
            log.info("Method cardInformation: completed successfully.");
            return cardRepository.findAllById(usersId);
        }
        log.warn("Method cardInformation: something went wrong!");
        return null;
    }

    @Override
    public Card createCard(CreateCardRequest request, Principal principal) {
        User user = userRepository.findByLogin(PrincipalUtil.getUsername(principal));
        Card card = conversionService.convert(request, Card.class);
        if (user.getBankAccounts().contains(bankAccountRepository.findById(request.getIdBankAccount()).get())) {
            log.info("Method createCard: card created.");
            return cardRepository.save(card);
        }
        log.warn("Method createCard: card not created!");
        return card;
    }

    @Override
    public Card updateCard(UpdateCardRequest request) {
        Card result = conversionService.convert(request, Card.class);
        if (result == null) {
            log.warn("Method updateCard: card not updated!");
        } else {
            log.info("Method updateCard: card updated.");
        }
        return cardRepository.save(result);
    }

    @Override
    public Card blockedCard(String cardNumber, Principal principal) {
        User user = userRepository.findByLogin(PrincipalUtil.getUsername(principal));
        Card card = cardRepository.findByCardNumber(cardNumber);
        BankAccount bankAccount = card.getIdBankAccount();
        if (user.getId() == bankAccount.getUserId().getId()) {
            card.setBlocked(true);
            log.info("Method blockedCard: card blocked.");
            return cardRepository.save(card);
        }
        log.warn("Method blockedCard: card not blocked!");
        return card;
    }

    @Override
    public void deleteCardById(Long id) {
        cardRepository.deleteCardById(id);
        log.info("Method deleteCardById: card deleted.");
    }
}