package by.park.service.impl;

import by.park.controller.request.CreateBankRequest;
import by.park.controller.request.UpdateBankRequest;
import by.park.domain.Bank;
import by.park.repository.BankRepository;
import by.park.service.BankService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BankServiceImpl implements BankService {

    BankRepository bankRepository;

    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public List<Bank> findAllBanks() {
        return bankRepository.findAll();
    }

    @Override
    public Optional<Bank> findBankById(Long id) {
        return bankRepository.findById(id);
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
    public Bank createBank(CreateBankRequest createBankRequest) {
        Bank bank = new Bank();
        bank.setBankName(createBankRequest.getBankName());
        bank.setPhoneNumber(createBankRequest.getPhoneNumber());
        bank.setBankCode(createBankRequest.getBankCode());
        bank.setCreated(new Timestamp(new Date().getTime()));
        bank.setChanged(new Timestamp(new Date().getTime()));
        bank.setDeleted(false);
        return bankRepository.save(bank);
    }

    @Override
    public Bank updateBank(UpdateBankRequest updateBankRequest) {
        Optional<Bank> oldBank = bankRepository.findById(updateBankRequest.getId());

        Bank bank = new Bank();
        bank.setId(updateBankRequest.getId());
        bank.setBankName(updateBankRequest.getBankName());
        bank.setPhoneNumber(updateBankRequest.getPhoneNumber());
        bank.setBankCode(updateBankRequest.getBankCode());
        bank.setCreated(oldBank.get().getCreated());
        bank.setChanged(new Timestamp(new Date().getTime()));
        bank.setDeleted(updateBankRequest.getDeleted());
        return bankRepository.save(bank);
    }

    @Override
    public void deleteBankById(Long id) {
        bankRepository.deleteBankById(id);
    }
}
