package by.park.controller.converter;

import by.park.controller.request.UpdateBankRequest;
import by.park.domain.Bank;
import org.springframework.stereotype.Component;

@Component
public class BankUpdateRequestConverter extends BankRequestConverter<UpdateBankRequest, Bank>{
    @Override
    public Bank convert(UpdateBankRequest request) {
        Bank bank = entityManager.find(Bank.class,request.getId());
        return doConvert(bank,request);
    }
}
