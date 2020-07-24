package by.park.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("User updation model")
public class UpdateUserRequest {

    @ApiModelProperty(required = true, dataType = "long", notes = "user id")
    private Long id;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 100)
    @ApiModelProperty(required = true, dataType = "string", notes = "user firstname")
    private String firstname;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 100)
    @ApiModelProperty(required = true, dataType = "string", notes = "user lastname")
    private String lastname;

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

    @ApiModelProperty(required = true, dataType = "boolean", notes = "deleted user or not")
    private Boolean deleted;
}