package by.park.service.impl;

import by.park.controller.request.CreateBankAccountRequest;
import by.park.controller.request.UpdateBankAccountRequest;
import by.park.domain.Bank;
import by.park.domain.BankAccount;
import by.park.domain.User;
import by.park.repository.BankAccountRepository;
import by.park.repository.BankRepository;
import by.park.repository.UserRepository;
import by.park.service.BankAccountService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    BankAccountRepository bankAccountRepository;
    UserRepository userRepository;
    BankRepository bankRepository;

    public BankAccountServiceImpl(BankRepository bankRepository, BankAccountRepository bankAccountRepository, UserRepository userRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
        this.bankRepository = bankRepository;
    }

    @Override
    public List<BankAccount> findAll() {
        return bankAccountRepository.findAll();
    }

    @Override
    public Optional<BankAccount> findById(Long id) {
        return bankAccountRepository.findById(id);
    }

    @Override
    public BankAccount findBankAccountByIban(String IBAN) {
        return bankAccountRepository.findBankAccountByIban(IBAN);
    }

    @Override
    public BankAccount createBankAccount(CreateBankAccountRequest createBankAccountRequest) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban(createBankAccountRequest.getIBAN());
        bankAccount.setAmount(createBankAccountRequest.getAmount());
        bankAccount.setCreated(new Timestamp(new Date().getTime()));
        bankAccount.setChanged(new Timestamp(new Date().getTime()));
        Optional<User> user = userRepository.findById(createBankAccountRequest.getIdUser());
        bankAccount.setUserId(user.get());
        Optional<Bank> bank = bankRepository.findById(createBankAccountRequest.getIdBank());
        bankAccount.setIdBank(bank.get());
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public BankAccount updateBankAccount(UpdateBankAccountRequest updateBankAccountRequest) {
        BankAccount bankAccount = new BankAccount();
        Optional<BankAccount> oldBankAccount = bankAccountRepository.findById(updateBankAccountRequest.getId());
        bankAccount.setId(updateBankAccountRequest.getId());
        bankAccount.setIban(updateBankAccountRequest.getIBAN());
        bankAccount.setAmount(updateBankAccountRequest.getAmount());
        bankAccount.setCreated(oldBankAccount.get().getCreated());
        bankAccount.setChanged(new Timestamp(new Date().getTime()));
        Optional<User> user = userRepository.findById(updateBankAccountRequest.getIdUser());
        bankAccount.setUserId(user.get());
        Optional<Bank> bank = bankRepository.findById(updateBankAccountRequest.getIdBank());
        bankAccount.setIdBank(bank.get());
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public void deleteBankAccountById(Long id) {
        bankAccountRepository.deleteBankAccountById(id);
    }
}
