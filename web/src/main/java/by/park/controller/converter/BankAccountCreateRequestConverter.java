package by.park.controller.converter;

import by.park.controller.request.CreateBankAccountRequest;
import by.park.domain.BankAccount;
import by.park.repository.BankRepository;
import by.park.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

import static by.park.util.MethodsForCreating.createIBAN;

@Component
public class BankAccountCreateRequestConverter extends BankAccountRequestConverter<CreateBankAccountRequest, BankAccount> {
    public BankAccountCreateRequestConverter(BankRepository bankRepository, UserRepository userRepository) {
        super(bankRepository, userRepository);
    }

    @Override
    public BankAccount convert(CreateBankAccountRequest request) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban(createIBAN(bankRepository.findBankByBankName(request.getBankName()).getBankCode()));
        bankAccount.setAmount(0L);
        bankAccount.setCreated(new Timestamp(new Date().getTime()));
        return doConvert(bankAccount, request);
    }
}
