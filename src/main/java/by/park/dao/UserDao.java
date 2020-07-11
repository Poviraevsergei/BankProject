package by.park.dao;

import by.park.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> findAll();

    User findById(Long userId);

    User save(User user);

    Optional<User> findByLogin(String username);

    User update(User user);

    User delete(Long userId);
}
