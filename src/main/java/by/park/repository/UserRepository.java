package by.park.repository;

import by.park.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying
    @Query("update User u set u.deleted = true where u.id = :id")
    void deleteUserById(@Param("id") Long id);

    User findByUsername(String username);
}