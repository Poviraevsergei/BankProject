package by.park.service;

import by.park.controller.request.CreateCardRequest;
import by.park.controller.request.UpdateCardRequest;
import by.park.domain.Card;

import java.util.List;
import java.util.Optional;

public interface CardService {
    List<Card> findAll();

    Optional<Card> findCardById(Long id);

    Card createCard(CreateCardRequest createCardRequest);

    Card updateCard(UpdateCardRequest updateCardRequest);

    void deleteCardById(Long id);
}
