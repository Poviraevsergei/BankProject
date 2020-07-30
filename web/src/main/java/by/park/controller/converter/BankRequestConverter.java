package by.park.controller.converter;

import by.park.controller.request.CreateBankRequest;
import by.park.domain.Bank;

import java.sql.Timestamp;
import java.util.Date;

public abstract class BankRequestConverter<S, T> extends EntityConverter<S, T> {

    protected Bank doConvert(Bank bank, CreateBankRequest request) {
        bank.setBankName(request.getBankName());
        bank.setPhoneNumber(request.getPhoneNumber());
        bank.setBankCode(request.getBankCode());
        bank.setChanged(new Timestamp(new Date().getTime()));
        return bank;
    }
}
