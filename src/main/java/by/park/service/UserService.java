package by.park.service;

import by.park.controller.request.CreateUserRequest;
import by.park.controller.request.UpdateUserRequest;
import by.park.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

public interface UserService {

    Page<User> findAllUsers(Pageable pageable);

    User findUserById(Long id);

    User findUserByLogin(String login);

    User register(CreateUserRequest createUserRequest);

    User updateUser(UpdateUserRequest updateUserRequest);

    void deleteUserById(Long id);

    User userInformation(Principal principal);
}