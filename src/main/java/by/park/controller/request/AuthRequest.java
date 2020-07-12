package by.park.controller.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "Authentication request with login and password")
public class AuthRequest implements Serializable {
    private String username;
    private String password;
}
