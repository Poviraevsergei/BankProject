package by.park.service;

import by.park.controller.request.CreateUserRequest;
import by.park.controller.request.UpdateUserRequest;
import by.park.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAllUsers();

    Optional<User> findUserById(Long id);

    User findUserByUsername(String username);

    User register(CreateUserRequest createUserRequest);

    User updateUser(UpdateUserRequest updateUserRequest);

    void deleteUserById(Long id);
}
