package by.park.controller.converter;

import by.park.controller.request.TransferTransactionalRequest;
import by.park.domain.Transaction;
import by.park.repository.BankAccountRepository;
import by.park.repository.CardRepository;
import org.springframework.stereotype.Component;


@Component
public class TransactionTransferRequestConverter extends TransactionRequestConverter<TransferTransactionalRequest, Transaction> {
    public TransactionTransferRequestConverter(CardRepository cardRepository, BankAccountRepository bankAccountRepository) {
        super(cardRepository, bankAccountRepository);
    }

    @Override
    public Transaction convert(TransferTransactionalRequest request) {
        Transaction transaction = new Transaction();
        transaction.setTypeOfTransaction("Transfer to " + request.getToCardNumber() + ". " + request.getTypeOfTransaction());
        return doConvert(transaction, request);
    }
}
