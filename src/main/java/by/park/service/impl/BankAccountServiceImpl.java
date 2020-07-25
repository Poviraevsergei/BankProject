package by.park.service.impl;

import by.park.controller.request.CreateBankAccountRequest;
import by.park.controller.request.UpdateBankAccountRequest;
import by.park.domain.Bank;
import by.park.domain.BankAccount;
import by.park.domain.User;
import by.park.repository.BankAccountRepository;
import by.park.repository.BankRepository;
import by.park.repository.UserRepository;
import by.park.security.util.PrincipalUtil;
import by.park.service.BankAccountService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static by.park.util.MethodsForCreating.createIBAN;

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
        return null;
    }

    @Override
    public BankAccount createBankAccount(CreateBankAccountRequest createBankAccountRequest, Principal principal) {
        BankAccount bankAccount = new BankAccount();
        Bank bank = bankRepository.findBankByBankName(createBankAccountRequest.getBankName());
        User user = userRepository.findByLogin(PrincipalUtil.getUsername(principal));
        bankAccount.setIban(createIBAN(bank.getBankCode()));
        bankAccount.setAmount(0L);
        bankAccount.setCreated(new Timestamp(new Date().getTime()));
        bankAccount.setChanged(new Timestamp(new Date().getTime()));
        bankAccount.setUserId(user);
        bankAccount.setIdBank(bank);
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public BankAccount updateBankAccount(UpdateBankAccountRequest updateBankAccountRequest) {
        BankAccount bankAccount = new BankAccount();
        BankAccount oldBankAccount = bankAccountRepository.findById(updateBankAccountRequest.getId()).get();
        User user = userRepository.findById(updateBankAccountRequest.getIdUser()).get();
        Bank bank = bankRepository.findById(updateBankAccountRequest.getIdBank()).get();
        bankAccount.setId(updateBankAccountRequest.getId());
        bankAccount.setIban(updateBankAccountRequest.getIBAN());
        bankAccount.setAmount(updateBankAccountRequest.getAmount());
        bankAccount.setCreated(oldBankAccount.getCreated());
        bankAccount.setChanged(new Timestamp(new Date().getTime()));
        bankAccount.setUserId(user);
        bankAccount.setIdBank(bank);
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public void deleteBankAccountById(Long id) {
        bankAccountRepository.deleteBankAccountById(id);
    }
}