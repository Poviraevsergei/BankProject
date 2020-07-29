package by.park.service.impl;

import by.park.controller.request.PayingTransactionRequest;
import by.park.controller.request.TransferTransactionalRequest;
import by.park.domain.BankAccount;
import by.park.domain.Card;
import by.park.domain.Transaction;
import by.park.domain.User;
import by.park.repository.BankAccountRepository;
import by.park.repository.CardRepository;
import by.park.repository.TransactionRepository;
import by.park.repository.UserRepository;
import by.park.security.util.PrincipalUtil;
import by.park.service.TransactionService;
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
public class TransactionServiceImpl implements TransactionService {

    TransactionRepository transactionRepository;
    BankAccountRepository bankAccountRepository;
    CardRepository cardRepository;
    UserRepository userRepository;
    ConversionService conversionService;

    public TransactionServiceImpl(ConversionService conversionService, UserRepository userRepository, TransactionRepository transactionRepository, CardRepository cardRepository, BankAccountRepository bankAccountRepository) {
        this.transactionRepository = transactionRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.conversionService = conversionService;
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
    public String paying(PayingTransactionRequest request, Principal principal) {
        User user = userRepository.findByLogin(PrincipalUtil.getUsername(principal));
        BankAccount bankAccount = bankAccountRepository.findById(
                cardRepository.findByCardNumber(request.getFromCardNumber()).getIdBankAccount().getId()
        ).get();
        Card cardFrom = cardRepository.findByCardNumber(request.getFromCardNumber());
        String userRole = user.getRoles().stream().findFirst().get().getUserRole();
        if (bankAccount.getAmount() >= request.getCount() &&
                bankAccount.getUserId() == userRepository.findByLogin(PrincipalUtil.getUsername(principal)) && !cardFrom.getBlocked()
                || userRole.equals("ROLE_ADMIN")) {
            Transaction transaction = conversionService.convert(request, Transaction.class);
            if (transaction != null) {
                bankAccount.setAmount(bankAccount.getAmount() - request.getCount());
                transactionRepository.save(transaction);
                log.info("Method paying: payment completed successfully.");
                return "Payment completed successfully";
            }
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
    public String transfer(TransferTransactionalRequest request, Principal principal) {
        User user = userRepository.findByLogin(PrincipalUtil.getUsername(principal));
        BankAccount bankAccountFrom = bankAccountRepository.findById(cardRepository.findByCardNumber(request.getFromCardNumber()).getIdBankAccount().getId()).get();
        BankAccount bankAccountTo = bankAccountRepository.findById(cardRepository.findByCardNumber(request.getToCardNumber()).getIdBankAccount().getId()).get();
        Card cardTo = cardRepository.findByCardNumber(request.getToCardNumber());
        Card cardFrom = cardRepository.findByCardNumber(request.getFromCardNumber());
        String userRole = user.getRoles().stream().findFirst().get().getUserRole();
        if (bankAccountFrom.getAmount() >= request.getCount() &&
                bankAccountFrom.getUserId() == userRepository.findByLogin(PrincipalUtil.getUsername(principal)) && !cardFrom.getBlocked() && !cardTo.getBlocked()
                || userRole.equals("ROLE_ADMIN")
        ) {
            Transaction transaction = conversionService.convert(request, Transaction.class);
            if (transaction != null) {
                bankAccountFrom.setAmount(
                        bankAccountFrom.getAmount() - request.getCount());
                bankAccountTo.setAmount(
                        bankAccountTo.getAmount() + request.getCount());
                transactionRepository.save(transaction);
                log.info("Method transfer: money transfer was successful.");
                return "Money transfer was successful!";
            }
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