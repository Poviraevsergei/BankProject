package by.park.controller.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "Authentication request with login and password")
public class AuthRequest {

    @NotEmpty
    @NotNull
    private String login;

    @NotEmpty
    @NotNull
    private String password;
}
