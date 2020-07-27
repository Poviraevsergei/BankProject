package by.park.service;

import by.park.controller.request.CreateCardRequest;
import by.park.controller.request.UpdateCardRequest;
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

    Card createCard(CreateCardRequest createCardRequest, Principal principal);

    Card updateCard(UpdateCardRequest updateCardRequest);

    Card blockedCard(String cardNumber, Principal principal);

    void deleteCardById(Long id);
}
