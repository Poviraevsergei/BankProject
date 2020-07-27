package by.park.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

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