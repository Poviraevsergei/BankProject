package by.park.service;

import by.park.domain.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(Long userId);

    User save(User user);

    User update(User user);

    User delete(Long userId);
}
