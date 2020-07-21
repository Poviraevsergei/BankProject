package by.park.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("User creation model")
public class CreateUserRequest {
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 100)
    @ApiModelProperty(required = true, dataType = "string", notes = "user first name")
    private String username;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 100)
    @ApiModelProperty(required = true, dataType = "string", notes = "user second name")
    private String surname;

    @ApiModelProperty(required = true, dataType = "data", notes = "user birthday name")
    private Date birthDate;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 100)
    @ApiModelProperty(required = true, dataType = "string", notes = "user login")
    private String login;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 100)
    @ApiModelProperty(required = true, dataType = "string", notes = "user password")
    private String password;

    @NotNull
    @NotEmpty
    @Size(min = 10, max = 50)
    @ApiModelProperty(required = true, dataType = "string", notes = "user passport number")
    private String passportNumber;
}
