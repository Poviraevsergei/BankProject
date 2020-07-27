package by.park.controller.converter;

import by.park.controller.request.PayingTransactionRequest;
import by.park.domain.Transaction;
import by.park.repository.BankAccountRepository;
import by.park.repository.CardRepository;
import org.springframework.stereotype.Component;

@Component
public class TransactionPayingRequestConverter extends TransactionRequestConverter<PayingTransactionRequest, Transaction> {
    public TransactionPayingRequestConverter(CardRepository cardRepository, BankAccountRepository bankAccountRepository) {
        super(cardRepository, bankAccountRepository);
    }

    @Override
    public Transaction convert(PayingTransactionRequest request) {
        Transaction transaction = new Transaction();
        transaction.setTypeOfTransaction(request.getTypeOfTransaction());
        return doConvert(transaction, request);
    }
}
