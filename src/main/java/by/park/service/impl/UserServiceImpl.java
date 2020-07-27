package by.park.service.impl;

import by.park.controller.request.CreateUserRequest;
import by.park.controller.request.UpdateUserRequest;
import by.park.domain.User;
import by.park.repository.BankRepository;
import by.park.repository.UserRepository;
import by.park.security.util.PrincipalUtil;
import by.park.service.UserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
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
        return userRepository.findAll(pageable);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User findUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public User register(CreateUserRequest request) {
        User user = conversionService.convert(request, User.class);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(UpdateUserRequest request) {
        User user = conversionService.convert(request, User.class);
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteUserById(id);
    }

    @Override
    public User userInformation(Principal principal) {
        User user = null;
        if (!findUserByLogin(PrincipalUtil.getUsername(principal)).getDeleted()) {
            return findUserByLogin(PrincipalUtil.getUsername(principal));
        }
        return user;
    }
}
