package by.park.service;

import by.park.controller.request.CreateCardRequest;
import by.park.controller.request.UpdateCardRequest;
import by.park.domain.Card;

import java.security.Principal;
import java.util.List;

public interface CardService {
    List<Card> findAll();

    Card findCardById(Long id);

    Card findByCardNumber(String cardNumber);

    List<Card> cardInformation(Principal principal);

    Card createCard(CreateCardRequest createCardRequest);

    Card updateCard(UpdateCardRequest updateCardRequest);

    void deleteCardById(Long id);

}
