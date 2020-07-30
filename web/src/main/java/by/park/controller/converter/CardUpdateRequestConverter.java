package by.park.controller.converter;

import by.park.controller.request.UpdateCardRequest;
import by.park.domain.Card;
import by.park.repository.BankAccountRepository;
import org.springframework.stereotype.Component;

@Component
public class CardUpdateRequestConverter extends CardRequestConverter<UpdateCardRequest, Card> {
    public CardUpdateRequestConverter(BankAccountRepository bankAccountRepository) {
        super(bankAccountRepository);
    }

    @Override
    public Card convert(UpdateCardRequest request) {
        Card card = entityManager.find(Card.class, request.getId());
        card.setCardNumber(request.getCardNumber());
        return doConvert(card, request);
    }
}