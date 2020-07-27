package by.park.controller.converter;

import by.park.controller.request.UpdateBankAccountRequest;
import by.park.domain.BankAccount;
import by.park.repository.BankRepository;
import by.park.repository.UserRepository;
import org.springframework.stereotype.Component;


@Component
public class BankAccountUpdateRequestConverter extends BankAccountRequestConverter<UpdateBankAccountRequest, BankAccount> {
    public BankAccountUpdateRequestConverter(BankRepository bankRepository, UserRepository userRepository) {
        super(bankRepository, userRepository);
    }

    @Override
    public BankAccount convert(UpdateBankAccountRequest request) {
        BankAccount bankAccount = entityManager.find(BankAccount.class, request.getId());
        bankAccount.setIban(request.getIBAN());
        bankAccount.setAmount(request.getAmount());
        return doConvert(bankAccount, request);
    }
}
