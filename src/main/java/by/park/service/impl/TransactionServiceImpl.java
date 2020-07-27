package by.park.service.impl;

import by.park.controller.request.PayingTransactionRequest;
import by.park.controller.request.TransferTransactionalRequest;
import by.park.domain.BankAccount;
import by.park.domain.Transaction;
import by.park.domain.User;
import by.park.repository.BankAccountRepository;
import by.park.repository.CardRepository;
import by.park.repository.TransactionRepository;
import by.park.repository.UserRepository;
import by.park.security.util.PrincipalUtil;
import by.park.service.TransactionService;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
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
    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public String paying(PayingTransactionRequest request, Principal principal) {
        BankAccount bankAccount = bankAccountRepository.findById(
                cardRepository.findByCardNumber(request.getFromCardNumber()).getIdBankAccount().getId()
        ).get();
        if (bankAccount.getAmount() >= request.getCount() &&
                bankAccount.getUserId() == userRepository.findByLogin(PrincipalUtil.getUsername(principal))) {
            Transaction transaction = conversionService.convert(request, Transaction.class);
            if (transaction != null) {
                bankAccount.setAmount(bankAccount.getAmount() - request.getCount());
                transactionRepository.save(transaction);
                return "Payment completed successfully";
            }
        }
        return "There was a problem with payment";
    }

    @Override
    public List<Transaction> transactionInformation(Principal principal) {
        User user = userRepository.findByLogin(PrincipalUtil.getUsername(principal));
        Set<BankAccount> bankAccount = user.getBankAccounts();
        Set<Transaction> userTransactions = bankAccount.stream().map(BankAccount::getTransactions).findAny().get();
        Set<Long> usersId = userTransactions.stream().map(Transaction::getId).collect(Collectors.toSet());
        if (!user.getDeleted()) {
            return transactionRepository.findAllById(usersId);
        }
        return Collections.emptyList();
    }

    @Override
    public String transfer(TransferTransactionalRequest request, Principal principal) {
        BankAccount bankAccountFrom = bankAccountRepository.findById(cardRepository.findByCardNumber(request.getFromCardNumber()).getIdBankAccount().getId()).get();
        BankAccount bankAccountTo = bankAccountRepository.findById(cardRepository.findByCardNumber(request.getToCardNumber()).getIdBankAccount().getId()).get();
        if (bankAccountFrom.getAmount() >= request.getCount() &&
                bankAccountFrom.getUserId() == userRepository.findByLogin(PrincipalUtil.getUsername(principal))
        ) {
            Transaction transaction = conversionService.convert(request, Transaction.class);
            if (transaction != null) {
                bankAccountFrom.setAmount(
                        bankAccountFrom.getAmount() - request.getCount());
                bankAccountTo.setAmount(
                        bankAccountTo.getAmount() + request.getCount());
                transactionRepository.save(transaction);
                return "Money transfer was successful!";
            }
        }
        return "There was a problem with money transfer";
    }

    @Override
    public Transaction findTransactionById(Long id) {
        return transactionRepository.findById(id).get();
    }

    @Override
    public void deleteTransactionById(Long id) {
        transactionRepository.deleteTransactionById(id);
    }
}
