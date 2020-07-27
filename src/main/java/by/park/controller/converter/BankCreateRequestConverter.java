package by.park.controller.converter;

import by.park.controller.request.CreateBankRequest;
import by.park.domain.Bank;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

@Component
public class BankCreateRequestConverter extends BankRequestConverter<CreateBankRequest, Bank> {
    @Override
    public Bank convert(CreateBankRequest request) {
        Bank bank = new Bank();
        bank.setCreated(new Timestamp(new Date().getTime()));
        return doConvert(bank,request);
    }
}
