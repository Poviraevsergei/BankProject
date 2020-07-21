package by.park.service.impl;

import by.park.controller.request.CreateUserRequest;
import by.park.controller.request.UpdateUserRequest;
import by.park.domain.User;
import by.park.repository.UserRepository;
import by.park.service.UserService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User register(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setUsername(createUserRequest.getUsername());
        user.setSurname(createUserRequest.getSurname());
        user.setBirthDate(createUserRequest.getBirthDate());
        user.setLogin(createUserRequest.getLogin());
        user.setCreated(new Timestamp(new Date().getTime()));
        user.setChanged(new Timestamp(new Date().getTime()));
        user.setDeleted(false);
        user.setPassword(createUserRequest.getPassword());
        user.setPassportNumber(createUserRequest.getPassportNumber());
        return userRepository.save(user);
    }

    @Override
    public User updateUser(UpdateUserRequest updateUserRequest) {
        Optional<User> oldUser = userRepository.findById(updateUserRequest.getId());
        User user = new User();
        user.setId(updateUserRequest.getId());
        user.setUsername(updateUserRequest.getUsername());
        user.setSurname(updateUserRequest.getSurname());
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
