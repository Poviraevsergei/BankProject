package by.park.dao.repository;

import by.park.dao.UserDao;
import by.park.domain.User;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository("userDaoRepository")
public class UserRepository implements UserDao {
    JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static final String USER_ID = "id";
    public static final String USER_NAME = "user_name";
    public static final String USER_SURNAME = "user_surname";
    public static final String USER_BIRTH_DATE = "birth_date";
    public static final String USER_LOGIN = "user_login";
    public static final String USER_PASSWORD = "user_password";
    public static final String USER_PASSPORT = "passport_number";
    public static final String USER_CREATED = "created";
    public static final String USER_CHANGED = "changed";
    public static final String USER_IS_DELETED = "is_deleted";

    @Override
    public List<User> findAll() {
        final String findAllQuery = "select * from m_users order by id desc";
        return jdbcTemplate.query(findAllQuery, this::userRowMapper);
    }

    @Override
    public User findById(Long userId) {
        final String findById = "select * from m_users where id = ?";
        return jdbcTemplate.queryForObject(findById, this::userRowMapper, userId);
    }

    @Override
    public User save(User user) {
        final String saveQuery = "INSERT INTO m_users(user_name,user_surname,birth_date,user_login,user_password,passport_number) values(?,?,?,?,?,?)";
        final String getLastId = "SELECT currval('m_users_id_seq') as last_insert_id;";
        jdbcTemplate.update(saveQuery, user.getUserName(), user.getSurname(), user.getBirthDate(), user.getLogin(), user.getPassword(), user.getPassportNumber());
        Long lastUserId = jdbcTemplate.queryForObject(getLastId, Long.class);
        return findById(lastUserId);
    }

    @Override
    public Optional<User> findByLogin(String username) {
        try {
            final String findByLogin = "select * from m_users where user_login = ?";
            return Optional.of(jdbcTemplate.queryForObject(findByLogin, this::userRowMapper));
        } catch (DataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public User update(User user) {
        final String updateQuery = "update m_users set user_name = ?,user_surname = ?,birth_date = ?,user_login = ?" +
                ",user_password = ?,passport_number = ?,is_deleted = ? where id = ?";
        jdbcTemplate.update(updateQuery, user.getUserName(), user.getSurname(), user.getBirthDate(), user.getLogin(), user.getPassword(), user.getPassportNumber(), user.getDeleted(), user.getId());
        return findById(user.getId());
    }

    @Override
    public User delete(Long userId) {
        final String deleteQuery = "update m_users set is_deleted = true where id = ?";
        jdbcTemplate.update(deleteQuery, userId);
        return findById(userId);
    }

    private User userRowMapper(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong(USER_ID));
        user.setUserName(resultSet.getString(USER_NAME));
        user.setSurname(resultSet.getString(USER_SURNAME));
        user.setBirthDate(resultSet.getDate(USER_BIRTH_DATE));
        user.setLogin(resultSet.getString(USER_LOGIN));
        user.setPassword(resultSet.getString(USER_PASSWORD));
        user.setPassportNumber(resultSet.getString(USER_PASSPORT));
        user.setCreated(resultSet.getTimestamp(USER_CREATED));
        user.setChanged(resultSet.getTimestamp(USER_CHANGED));
        user.setDeleted(resultSet.getBoolean(USER_IS_DELETED));
        return user;
    }
}