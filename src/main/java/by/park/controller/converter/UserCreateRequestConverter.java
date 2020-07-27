package by.park.controller.converter;

import by.park.controller.request.CreateUserRequest;
import by.park.domain.BankAccount;
import by.park.domain.Role;
import by.park.domain.Roles;
import by.park.domain.User;
import by.park.repository.BankRepository;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;

import static by.park.util.MethodsForCreating.createIBAN;

@Component
public class UserCreateRequestConverter extends UserRequestConverter<CreateUserRequest, User> {

    BankRepository bankRepository;

    public UserCreateRequestConverter(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public User convert(CreateUserRequest request) {
        User user = new User();
        user.setCreated(new Timestamp(new Date().getTime()));
        user.setDeleted(false);

        Role role = new Role();
        role.setUserRole(Roles.ROLE_USER.name());
        role.setUserId(user);
        user.setRoles(Collections.singleton(role));
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAmount(0L);
        bankAccount.setIban(createIBAN(bankRepository.findBankByBankName(request.getBankName()).getBankCode()));
        bankAccount.setUserId(user);
        bankAccount.setCreated(new Timestamp(new Date().getTime()));
        bankAccount.setIdBank(bankRepository.findBankByBankName(request.getBankName()));
        user.setBankAccounts(Collections.singleton(bankAccount));
        return doConvert(user, request);
    }
}
