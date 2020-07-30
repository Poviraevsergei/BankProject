package by.park.service.impl;

import by.park.domain.BankAccount;
import by.park.domain.Card;
import by.park.domain.Transaction;
import by.park.domain.User;
import by.park.repository.BankAccountRepository;
import by.park.repository.CardRepository;
import by.park.repository.TransactionRepository;
import by.park.repository.UserRepository;
import by.park.service.TransactionService;
import by.park.util.PrincipalUtil;
import lombok.extern.slf4j.Slf4j;
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
public class TransactionServiceImpl implements TransactionService {

    TransactionRepository transactionRepository;
    BankAccountRepository bankAccountRepository;
    CardRepository cardRepository;
    UserRepository userRepository;

    public TransactionServiceImpl(UserRepository userRepository, TransactionRepository transactionRepository, CardRepository cardRepository, BankAccountRepository bankAccountRepository) {
        this.transactionRepository = transactionRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Page<Transaction> findAllTransactions(Pageable pageable) {
        Page<Transaction> result = transactionRepository.findAll(pageable);
        if (result.getTotalElements() == 0) {
            log.warn("Method findAllTransactions: transaction not found !");
        } else {
            log.info("Method findAllTransactions: transaction found.");
        }
        return result;
    }

    @Override
    public Transaction findTransactionById(Long id) {
        Optional<Transaction> result = transactionRepository.findById(id);
        if (result.isPresent()) {
            log.info("Method findTransactionById: transaction found.");
        } else {
            log.warn("Method findTransactionById: transaction not found !");
        }
        return result.get();
    }

    @Override
    public String paying(Transaction transaction, Card card, Principal principal) {
        User user = userRepository.findByLogin(PrincipalUtil.getUsername(principal));
        BankAccount bankAccount = bankAccountRepository.findById(card.getIdBankAccount().getId()).get();
        String userRole = user.getRoles().stream().findFirst().get().getUserRole();
        if (bankAccount.getAmount() >= transaction.getCount() &&
                bankAccount.getUserId() == userRepository.findByLogin(PrincipalUtil.getUsername(principal)) && !user.getDeleted() && !card.getBlocked()
                || userRole.equals("ROLE_ADMIN")) {

            bankAccount.setAmount(bankAccount.getAmount() - transaction.getCount());
            transactionRepository.save(transaction);
            log.info("Method paying: payment completed successfully.");
            return "Payment completed successfully";

        }
        log.warn("Method paying: there was a problem with payment");
        return "There was a problem with payment";
    }

    @Override
    public List<Transaction> transactionInformation(Principal principal) {
        User user = userRepository.findByLogin(PrincipalUtil.getUsername(principal));
        Set<BankAccount> bankAccount = user.getBankAccounts();
        Set<Transaction> userTransactions = bankAccount.stream().map(BankAccount::getTransactions).findAny().get();
        Set<Long> usersId = userTransactions.stream().map(Transaction::getId).collect(Collectors.toSet());
        if (!user.getDeleted()) {
            log.info("Method transactionInformation: completed successfully.");
            return transactionRepository.findAllById(usersId);
        }
        log.warn("Method transactionInformation: something went wrong!");
        return Collections.emptyList();
    }

    @Override
    public String transfer(Transaction transaction, Card cardFrom, Card cardTo, Principal principal) {
        User user = userRepository.findByLogin(PrincipalUtil.getUsername(principal));
        BankAccount bankAccountFrom = bankAccountRepository.findById(cardFrom.getIdBankAccount().getId()).get();
        BankAccount bankAccountTo = bankAccountRepository.findById(cardTo.getIdBankAccount().getId()).get();
        String userRole = user.getRoles().stream().findFirst().get().getUserRole();
        if (bankAccountFrom.getAmount() >= transaction.getCount() && !user.getDeleted() &&
                bankAccountFrom.getUserId() == userRepository.findByLogin(PrincipalUtil.getUsername(principal)) && !cardFrom.getBlocked() && !cardTo.getBlocked()
                || userRole.equals("ROLE_ADMIN")
        ) {
            bankAccountFrom.setAmount(
                    bankAccountFrom.getAmount() - transaction.getCount());
            bankAccountTo.setAmount(
                    bankAccountTo.getAmount() + transaction.getCount());
            transactionRepository.save(transaction);
            log.info("Method transfer: money transfer was successful.");
            return "Money transfer was successful!";
        }
        log.warn("Method transfer: there was a problem with money transfer!");
        return "There was a problem with money transfer";
    }

    @Override
    public void deleteTransactionById(Long id) {
        transactionRepository.deleteTransactionById(id);
        log.info("Method deleteTransactionById: transaction deleted.");
    }
}