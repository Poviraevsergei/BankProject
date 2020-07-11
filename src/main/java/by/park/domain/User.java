package by.park.domain;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Data
public class User implements Serializable {
    private Long id;
    private String userName;
    private String surname;
    private Date birthDate;
    private String login;
    private String password;
    private String passportNumber;
    private Timestamp created;
    private Timestamp changed;
    private Boolean deleted;
}