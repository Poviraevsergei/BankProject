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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("Bank updation model")
public class UpdateBankRequest {

    @ApiModelProperty(required = true, dataType = "long", notes = "bank id")
    private Long id;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 100)
    @ApiModelProperty(required = true, dataType = "string", notes = "bank name")
    private String bankName;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 100)
    @ApiModelProperty(required = true, dataType = "string", notes = "bank phone number")
    private String phoneNumber;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 100)
    @ApiModelProperty(required = true, dataType = "string", notes = "bank code")
    private String bankCode;

    @ApiModelProperty(required = true, dataType = "boolean", notes = "deleted bank or not")
    private Boolean deleted;
}
