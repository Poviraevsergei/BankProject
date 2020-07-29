package by.park.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("User updation model")
public class UpdateUserRequest extends CreateUserRequest {

    @ApiModelProperty(required = true, dataType = "long", notes = "user id")
    private Long id;

    @ApiModelProperty(required = true, dataType = "boolean", notes = "deleted user or not")
    private Boolean deleted;
}