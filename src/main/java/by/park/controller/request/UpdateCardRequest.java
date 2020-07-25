package by.park.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("Card updating model")
public class UpdateCardRequest {

    @ApiModelProperty(required = true, dataType = "long", notes = "card id")
    private Long id;

    @NotNull
    @NotEmpty
    @Size(min = 19, max = 19)
    @ApiModelProperty(required = true, dataType = "string", notes = "card number")
    private String cardNumber;

    @Min(1)
    @ApiModelProperty(required = true, dataType = "long", notes = "bank id")
    private Long idBankAccount;

    @NotNull
    @NotEmpty
    @Size(min = 4, max = 100)
    @ApiModelProperty(required = true, dataType = "string", notes = "card type")
    private String cardType;
}