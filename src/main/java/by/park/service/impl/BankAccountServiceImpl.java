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
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    BankAccountRepository bankAccountRepository;
    UserRepository userRepository;
    BankRepository bankRepository;
    ConversionService conversionService;

    public BankAccountServiceImpl(ConversionService conversionService, BankRepository bankRepository, BankAccountRepository bankAccountRepository, UserRepository userRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
        this.bankRepository = bankRepository;
        this.conversionService = conversionService;
    }

    @Override
    public Page<BankAccount> findAll(Pageable pageable) {
        return bankAccountRepository.findAll(pageable);
    }

    @Override
    public BankAccount findById(Long id) {
        return bankAccountRepository.findById(id).get();
    }

    @Override
    public BankAccount findBankAccountByIban(String IBAN) {
        return bankAccountRepository.findBankAccountByIban(IBAN);
    }

    @Override
    public List<BankAccount> bankAccountInformation(Principal principal) {
        User user = userRepository.findByLogin(PrincipalUtil.getUsername(principal));
        Set<BankAccount> bankAccount = user.getBankAccounts();
        Set<Long> usersId = bankAccount.stream().map(BankAccount::getId).collect(Collectors.toSet());
        if (!user.getDeleted()) {
            return bankAccountRepository.findAllById(usersId);
        }
        return Collections.emptyList();
    }

    @Override
    public BankAccount createBankAccount(CreateBankAccountRequest request) {
        BankAccount bankAccount = conversionService.convert(request, BankAccount.class);
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public BankAccount updateBankAccount(UpdateBankAccountRequest request) {
        BankAccount bankAccount = conversionService.convert(request, BankAccount.class);
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public void deleteBankAccountById(Long id) {
        bankAccountRepository.deleteBankAccountById(id);
    }
}