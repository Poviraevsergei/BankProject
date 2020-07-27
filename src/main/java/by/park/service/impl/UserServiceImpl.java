package by.park.service.impl;

import by.park.controller.request.CreateUserRequest;
import by.park.controller.request.UpdateUserRequest;
import by.park.domain.User;
import by.park.repository.BankRepository;
import by.park.repository.UserRepository;
import by.park.security.util.PrincipalUtil;
import by.park.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    BankRepository bankRepository;
    ConversionService conversionService;

    public UserServiceImpl(ConversionService conversionService, UserRepository userRepository, BankRepository bankRepository) {
        this.userRepository = userRepository;
        this.bankRepository = bankRepository;
        this.conversionService = conversionService;
    }

    @Override
    public Page<User> findAllUsers(Pageable pageable) {
        Page<User> result = userRepository.findAll(pageable);
        if (result.getTotalElements() == 0) {
            log.warn("Method findAllUsers: users not found !");
        } else {
            log.info("Method findAllUsers: users found.");
        }
        return result;
    }

    @Override
    public User findUserById(Long id) {
        Optional<User> result = userRepository.findById(id);
        if (result.isPresent()) {
            log.info("Method findUserById: user found.");
        } else {
            log.warn("Method findUserById: user not found !");
        }
        return result.get();
    }

    @Override
    public User findUserByLogin(String login) {
        User result = userRepository.findByLogin(login);
        if (result == null) {
            log.warn("Method findUserByLogin: user not found!");
        } else {
            log.info("Method findUserByLogin: user found.");
        }
        return result;
    }

    @Override
    public User userInformation(Principal principal) {
        User user = null;
        if (!findUserByLogin(PrincipalUtil.getUsername(principal)).getDeleted()) {
            log.info("Method userInformation: completed successfully.");
            return findUserByLogin(PrincipalUtil.getUsername(principal));
        }
        log.warn("Method userInformation: something went wrong!");
        return user;
    }

    @Override
    public User register(CreateUserRequest request) {
        User result = conversionService.convert(request, User.class);
        if (result == null) {
            log.warn("Method register: user not created!");
        } else {
            log.info("Method register: user created.");
        }
        return userRepository.save(result);
    }

    @Override
    public User updateUser(UpdateUserRequest request) {
        User result = conversionService.convert(request, User.class);
        if (result == null) {
            log.warn("Method updateUser: user not updated!");
        } else {
            log.info("Method updateUser: user updated.");
        }
        return userRepository.save(result);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteUserById(id);
        log.info("Method deleteUserById: user deleted.");
    }
}