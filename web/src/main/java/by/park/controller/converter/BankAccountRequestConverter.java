package by.park.controller.converter;

import by.park.controller.request.CreateBankAccountRequest;
import by.park.domain.Bank;
import by.park.domain.BankAccount;
import by.park.domain.User;
import by.park.repository.BankRepository;
import by.park.repository.UserRepository;

import java.sql.Timestamp;
import java.util.Date;

public abstract class BankAccountRequestConverter<S, T> extends EntityConverter<S, T> {
    BankRepository bankRepository;
    UserRepository userRepository;

    public BankAccountRequestConverter(BankRepository bankRepository, UserRepository userRepository) {
        this.bankRepository = bankRepository;
        this.userRepository = userRepository;
    }

    protected BankAccount doConvert(BankAccount bankAccount, CreateBankAccountRequest request) {
        Bank bank = bankRepository.findBankByBankName(request.getBankName());
        User user = userRepository.findByLogin(request.getUserLogin());
        bankAccount.setUserId(user);
        bankAccount.setIdBank(bank);
        bankAccount.setChanged(new Timestamp(new Date().getTime()));
        return bankAccount;
    }
}