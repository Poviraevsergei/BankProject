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
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@CacheConfig(cacheNames = {"banks", "banksById", "banksByName", "banksByCode"})
@Service
public class BankServiceImpl implements BankService {

    BankRepository bankRepository;
    ConversionService conversionService;
    UserRepository userRepository;

    public BankServiceImpl(ConversionService conversionService, BankRepository bankRepository, UserRepository userRepository) {
        this.bankRepository = bankRepository;
        this.userRepository = userRepository;
        this.conversionService = conversionService;
    }

    @Cacheable(value = "banks")
    @Override
    public Page<Bank> findAllBanks(Pageable pageable) {
        return bankRepository.findAll(pageable);
    }

    @Cacheable(value = "banksById")
    @Override
    public Bank findBankById(Long id) {
        return bankRepository.findById(id).get();
    }

    @Cacheable(value = "banksByName")
    @Override
    public Bank findBankByName(String bankName) {
        return bankRepository.findBankByBankName(bankName);
    }

    @Cacheable(value = "banksByCode")
    @Override
    public Bank findBankByBankCode(String bankCode) {
        return bankRepository.findBankByBankCode(bankCode);
    }

    @Override
    public List<Bank> bankInformation(Principal principal) {
        User user = userRepository.findByLogin(PrincipalUtil.getUsername(principal));
        if (!user.getDeleted()) {
            return bankRepository.findAllById(user.getBankAccounts().stream().map(BankAccount::getIdBank).map(Bank::getId).collect(Collectors.toList()));
        }
        return Collections.emptyList();
    }

    @Override
    public Bank createBank(CreateBankRequest request) {
        Bank bank = conversionService.convert(request, Bank.class);
        return bankRepository.save(bank);
    }

    @Override
    public Bank updateBank(UpdateBankRequest request) {
        Bank bank = conversionService.convert(request, Bank.class);
        return bankRepository.save(bank);
    }

    @Override
    public void deleteBankById(Long id) {
        bankRepository.deleteBankById(id);
    }
}