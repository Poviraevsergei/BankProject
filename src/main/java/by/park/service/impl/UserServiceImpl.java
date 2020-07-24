package by.park.service.impl;

import by.park.controller.request.CreateUserRequest;
import by.park.controller.request.UpdateUserRequest;
import by.park.domain.BankAccount;
import by.park.domain.Card;
import by.park.domain.Cards;
import by.park.domain.Roles;
import by.park.domain.Role;
import by.park.domain.User;
import by.park.repository.BankRepository;
import by.park.repository.UserRepository;
import by.park.service.UserService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static by.park.util.MethodsForCreating.createCardNumber;
import static by.park.util.MethodsForCreating.createIBAN;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    BankRepository bankRepository;

    public UserServiceImpl(UserRepository userRepository, BankRepository bankRepository) {
        this.userRepository = userRepository;
        this.bankRepository = bankRepository;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User findUserByUsername(String firstname) {
        return userRepository.findByFirstname(firstname);
    }

    @Override
    public User register(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setFirstname(createUserRequest.getFirstname());
        user.setLastname(createUserRequest.getLastname());
        user.setBirthDate(createUserRequest.getBirthDate());
        user.setLogin(createUserRequest.getLogin());
        user.setCreated(new Timestamp(new Date().getTime()));
        user.setChanged(new Timestamp(new Date().getTime()));
        user.setDeleted(false);
        user.setPassword(createUserRequest.getPassword());
        user.setPassportNumber(createUserRequest.getPassportNumber());

        Role role = new Role();
        role.setUserRole(Roles.ROLE_USER.name());
        role.setUserId(user);
        user.setRoles(Collections.singleton(role));
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAmount(0L);
        bankAccount.setIban(createIBAN(bankRepository.findBankByBankName(createUserRequest.getBankName()).getBankCode()));
        bankAccount.setUserId(user);
        bankAccount.setCreated(new Timestamp(new Date().getTime()));
        bankAccount.setChanged(new Timestamp(new Date().getTime()));
        bankAccount.setIdBank(bankRepository.findBankByBankName(createUserRequest.getBankName()));
        Card card = new Card();
        card.setCardType(Cards.DEBIT.name());
        card.setCardNumber(createCardNumber());
        card.setExpirationDate(new Timestamp(new Date().getTime()));
        card.setIdBankAccount(bankAccount);
        bankAccount.setCards(Collections.singleton(card));
        user.setBankAccounts(Collections.singleton(bankAccount));
        return userRepository.save(user);
    }

    @Override
    public User updateUser(UpdateUserRequest updateUserRequest) {
        Optional<User> oldUser = userRepository.findById(updateUserRequest.getId());
        User user = new User();
        user.setId(updateUserRequest.getId());
        user.setFirstname(updateUserRequest.getFirstname());
        user.setLastname(updateUserRequest.getLastname());
        user.setBirthDate(updateUserRequest.getBirthDate());
        user.setLogin(updateUserRequest.getLogin());
        user.setCreated(oldUser.get().getCreated());
        user.setChanged(new Timestamp(new Date().getTime()));
        user.setPassword(updateUserRequest.getPassword());
        user.setPassportNumber(updateUserRequest.getPassportNumber());
        user.setDeleted(updateUserRequest.getDeleted());
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteUserById(id);
    }


}
