package by.park.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Card updating model")
public class UpdateCardRequest extends CreateCardRequest {

    @ApiModelProperty(required = true, dataType = "long", notes = "card id")
    private Long id;

    @NotNull
    @NotEmpty
    @Size(min = 19, max = 19)
    @ApiModelProperty(required = true, dataType = "string", notes = "card number")
    private String cardNumber;
}