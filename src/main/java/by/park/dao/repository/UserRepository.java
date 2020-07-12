package by.park.dao.repository;

import by.park.dao.UserDao;
import by.park.domain.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository("userRepository")
public class UserRepository implements UserDao {

    private SessionFactory sessionFactory;

    private EntityManagerFactory entityManagerFactory;

    public UserRepository(SessionFactory sessionFactory, EntityManagerFactory entityManagerFactory) {
        this.sessionFactory = sessionFactory;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<User> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select user from User user order by user.id asc", User.class).list();
        }
    }

    @Override
    public User findById(Long userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(User.class, userId);
        }
    }

    @Override
    public User save(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.saveOrUpdate(user);
            return user;
        }
    }

    @Override
    public User update(User user) {
        return save(user);
    }

    @Override
    public User delete(Long userId) {
        return null;
    }
}