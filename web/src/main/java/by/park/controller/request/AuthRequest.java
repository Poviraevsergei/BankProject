package by.park.controller.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ApiModel(description = "Authentication request with login and password")
public class AuthRequest {

    @NotEmpty
    @NotNull
    @Size(min = 5, max = 100)
    private String login;

    @NotEmpty
    @NotNull
    @Size(min = 5, max = 100)
    private String password;
}