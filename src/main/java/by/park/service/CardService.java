package by.park.service;

import by.park.domain.BankAccount;
import by.park.domain.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;

public interface CardService {
    Page<Card> findAll(Pageable pageable);

    Card findCardById(Long id);

    Card findByCardNumber(String cardNumber);

    List<Card> cardInformation(Principal principal);

    Card createCard(Card card, BankAccount bankAccount, Principal principal);

    Card updateCard(Card card);

    Card blockedCard(String cardNumber, Principal principal);

    void deleteCardById(Long id);
}