package by.park.dao;

import by.park.domain.Card;

import java.util.List;

public interface CardDao {
    List<Card> findAll();

    Card findById(Long cardId);

    Card save(Card card);

    Card update(Card card);

    Card delete(Long cardId);
}
