package by.park.service;

import by.park.controller.request.CreateUserRequest;
import by.park.controller.request.UpdateUserRequest;
import by.park.domain.User;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAllUsers();

    Optional<User> findUserById(Long id);

    User findUserByLogin(String login);

    User register(CreateUserRequest createUserRequest);

    User updateUser(UpdateUserRequest updateUserRequest);

    void deleteUserById(Long id);

    User informationAboutUser(Principal principal);
}