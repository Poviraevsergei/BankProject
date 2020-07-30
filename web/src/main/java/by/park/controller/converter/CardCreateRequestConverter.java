package by.park.controller.converter;

import by.park.controller.request.CreateCardRequest;
import by.park.domain.Card;
import by.park.repository.BankAccountRepository;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import static by.park.util.MethodsForCreating.createCardNumber;

@Component
public class CardCreateRequestConverter extends CardRequestConverter<CreateCardRequest, Card> {

    public CardCreateRequestConverter(BankAccountRepository bankAccountRepository) {
        super(bankAccountRepository);
    }

    @Override
    public Card convert(CreateCardRequest request) {
        Card card = new Card();
        card.setCardNumber(createCardNumber());
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 1825);
        card.setExpirationDate(cal.getTime());
        card.setCreated(new Timestamp(new Date().getTime()));
        card.setBlocked(false);
        return doConvert(card, request);
    }
}