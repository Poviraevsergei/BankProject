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
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@CacheConfig(cacheNames = "banks")
public class BankServiceImpl implements BankService {

    BankRepository bankRepository;
    ConversionService conversionService;
    UserRepository userRepository;

    public BankServiceImpl(ConversionService conversionService, BankRepository bankRepository, UserRepository userRepository) {
        this.bankRepository = bankRepository;
        this.userRepository = userRepository;
        this.conversionService = conversionService;
    }

    @Cacheable("banks")
    @Override
    public Page<Bank> findAllBanks(Pageable pageable) {
        Page<Bank> result = bankRepository.findAll(pageable);
        if (result.getTotalElements() == 0) {
            log.warn("Method findAllBanks: banks not found !");
        } else {
            log.info("Method findAllBanks: banks found.");
        }
        return result;
    }

    @Override
    public Bank findBankById(Long id) {
        Optional<Bank> result = bankRepository.findById(id);
        if (result.isPresent()) {
            log.info("Method findBankById: bank found.");
        } else {
            log.warn("Method findBankById: bank not found !");
        }
        return result.get();
    }

    @Override
    public Bank findBankByName(String bankName) {
        Bank result = bankRepository.findBankByBankName(bankName);
        if (result == null) {
            log.warn("Method findBankByName: bank not found!");
        } else {
            log.info("Method findBankByName: bank found.");
        }
        return result;
    }

    @Override
    public Bank findBankByBankCode(String bankCode) {
        Bank result = bankRepository.findBankByBankCode(bankCode);
        if (result == null) {
            log.warn("Method findBankByBankCode: bank not found!");
        } else {
            log.info("Method findBankByBankCode: bank found.");
        }
        return result;
    }

    @Override
    public List<Bank> bankInformation(Principal principal) {
        User user = userRepository.findByLogin(PrincipalUtil.getUsername(principal));
        if (!user.getDeleted()) {
            log.info("Method bankInformation: completed successfully.");
            return bankRepository.findAllById(user.getBankAccounts().stream().map(BankAccount::getIdBank).map(Bank::getId).collect(Collectors.toList()));
        }
        log.warn("Method bankInformation: something went wrong!");
        return Collections.emptyList();
    }

    @Override
    public Bank createBank(CreateBankRequest request) {
        Bank result = conversionService.convert(request, Bank.class);
        if (result == null) {
            log.warn("Method createBank: bank not created!");
        } else {
            log.info("Method createBank: bank created.");
        }
        return bankRepository.save(result);
    }

    @Override
    public Bank updateBank(UpdateBankRequest request) {
        Bank result = conversionService.convert(request, Bank.class);
        if (result == null) {
            log.warn("Method updateBank: bank not updated!");
        } else {
            log.info("Method updateBank: bank updated.");
        }
        return bankRepository.save(result);
    }

    @Override
    public void deleteBankById(Long id) {
        bankRepository.deleteBankById(id);
        log.info("Method deleteBankById: bank deleted.");
    }
}