package by.park.controller.converter;

import by.park.controller.request.CreateUserRequest;
import by.park.domain.User;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.Date;


@Slf4j
public abstract class UserRequestConverter<S, T> extends EntityConverter<S, T> {

    protected User doConvert(User user, CreateUserRequest request) {
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setBirthDate(request.getBirthDate());
        user.setLogin(request.getLogin());
        user.setPassword(request.getPassword());
        user.setPassportNumber(request.getPassportNumber());
        user.setChanged(new Timestamp(new Date().getTime()));
        return user;
    }
}
