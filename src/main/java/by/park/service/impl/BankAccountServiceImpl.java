package by.park.service.impl;

import by.park.controller.request.CreateBankAccountRequest;
import by.park.controller.request.UpdateBankAccountRequest;
import by.park.domain.BankAccount;
import by.park.domain.User;
import by.park.repository.BankAccountRepository;
import by.park.repository.BankRepository;
import by.park.repository.UserRepository;
import by.park.security.util.PrincipalUtil;
import by.park.service.BankAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
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
    public Page<BankAccount> findAll(Pageable pageable) {
        Page<BankAccount> result = bankAccountRepository.findAll(pageable);
        if (result.getTotalElements() == 0) {
            log.warn("Method findAll: bank account not found !");
        } else {
            log.info("Method findAll: bank account found.");
        }
        return result;
    }

    @Override
    public BankAccount findById(Long id) {
        Optional<BankAccount> result = bankAccountRepository.findById(id);
        if (result.isPresent()) {
            log.info("Method findById: bank account found.");
        } else {
            log.warn("Method findById: bank account not found !");
        }
        return result.get();
    }

    @Override
    public BankAccount findBankAccountByIban(String IBAN) {
        BankAccount result = bankAccountRepository.findBankAccountByIban(IBAN);
        if (result == null) {
            log.warn("Method findBankAccountByIban: bank account not found!");
        } else {
            log.info("Method findBankAccountByIban: bank account found.");
        }
        return result;
    }

    @Override
    public List<BankAccount> bankAccountInformation(Principal principal) {
        User user = userRepository.findByLogin(PrincipalUtil.getUsername(principal));
        Set<BankAccount> bankAccount = user.getBankAccounts();
        Set<Long> usersId = bankAccount.stream().map(BankAccount::getId).collect(Collectors.toSet());
        if (!user.getDeleted()) {
            log.info("Method bankAccountInformation: completed successfully.");
            return bankAccountRepository.findAllById(usersId);
        }
        log.warn("Method bankAccountInformation: something went wrong!");
        return Collections.emptyList();
    }

    @Override
    public BankAccount createBankAccount(BankAccount bankAccount) {
        if (bankAccount == null) {
            log.warn("Method createBankAccount: bank account not created!");
        } else {
            log.info("Method createBankAccount: bank account created.");
        }
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public BankAccount updateBankAccount(BankAccount bankAccount) {
        if (bankAccount == null) {
            log.warn("Method updateBankAccount: bank account not updated!");
        } else {
            log.info("Method updateBankAccount: bank account updated.");
        }
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public void deleteBankAccountById(Long id) {
        bankAccountRepository.deleteBankAccountById(id);
        log.info("Method deleteBankAccountById: bank account deleted.");
    }
}