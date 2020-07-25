package by.park.service.impl;

import by.park.controller.request.CreateBankRequest;
import by.park.controller.request.UpdateBankRequest;
import by.park.domain.Bank;
import by.park.domain.BankAccount;
import by.park.domain.User;
import by.park.repository.BankRepository;
import by.park.repository.UserRepository;
import by.park.security.util.PrincipalUtil;
import by.park.service.BankService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BankServiceImpl implements BankService {

    BankRepository bankRepository;

    UserRepository userRepository;

    public BankServiceImpl(BankRepository bankRepository, UserRepository userRepository) {
        this.bankRepository = bankRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Bank> findAllBanks() {
        return bankRepository.findAll();
    }

    @Override
    public Bank findBankById(Long id) {
        return bankRepository.findById(id).get();
    }

    @Override
    public Bank findBankByName(String bankName) {
        return bankRepository.findBankByBankName(bankName);
    }

    @Override
    public Bank findBankByBankCode(String bankCode) {
        return bankRepository.findBankByBankCode(bankCode);
    }

    @Override
    public List<Bank> bankInformation(Principal principal) {
        User user = userRepository.findByLogin(PrincipalUtil.getUsername(principal));
        if(!user.getDeleted()){
            return bankRepository.findAllById(user.getBankAccounts().stream().map(BankAccount::getIdBank).map(Bank::getId).collect(Collectors.toList()));
        }
        return null;
    }

    @Override
    public Bank createBank(CreateBankRequest createBankRequest) {
        Bank bank = new Bank();
        bank.setBankName(createBankRequest.getBankName());
        bank.setPhoneNumber(createBankRequest.getPhoneNumber());
        bank.setBankCode(createBankRequest.getBankCode());
        bank.setCreated(new Timestamp(new Date().getTime()));
        bank.setChanged(new Timestamp(new Date().getTime()));
        return bankRepository.save(bank);
    }

    @Override
    public Bank updateBank(UpdateBankRequest updateBankRequest) {
        Bank oldBank = bankRepository.findById(updateBankRequest.getId()).get();

        Bank bank = new Bank();
        bank.setId(updateBankRequest.getId());
        bank.setBankName(updateBankRequest.getBankName());
        bank.setPhoneNumber(updateBankRequest.getPhoneNumber());
        bank.setBankCode(updateBankRequest.getBankCode());
        bank.setCreated(oldBank.getCreated());
        bank.setChanged(new Timestamp(new Date().getTime()));
        bank.setBankAccounts(oldBank.getBankAccounts());
        return bankRepository.save(bank);
    }

    @Override
    public void deleteBankById(Long id) {
        bankRepository.deleteBankById(id);
    }
}