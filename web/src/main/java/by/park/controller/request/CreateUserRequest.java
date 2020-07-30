package by.park.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("User creation model")
public class CreateUserRequest {

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 100)
    @ApiModelProperty(required = true, dataType = "string", notes = "user firstname")
    private String firstname;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 100)
    @ApiModelProperty(required = true, dataType = "string", notes = "user lastname")
    private String lastname;

    @ApiModelProperty(required = true, dataType = "data", notes = "user birthday name")
    private Date birthDate;

    @NotNull
    @NotEmpty
    @Size(min = 5, max = 100)
    @ApiModelProperty(required = true, dataType = "string", notes = "user login")
    private String login;

    @NotNull
    @NotEmpty
    @Size(min = 5, max = 100)
    @ApiModelProperty(required = true, dataType = "string", notes = "user password")
    private String password;

    @NotNull
    @NotEmpty
    @Size(min = 9, max = 9)
    @ApiModelProperty(required = true, dataType = "string", notes = "user passport number")
    private String passportNumber;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 50)
    @ApiModelProperty(required = true, dataType = "string", notes = "bank that user used")
    private String bankName;
}