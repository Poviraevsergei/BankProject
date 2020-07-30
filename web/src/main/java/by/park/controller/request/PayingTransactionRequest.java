package by.park.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Transaction paying model")
public class PayingTransactionRequest {

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 100)
    @ApiModelProperty(required = true, dataType = "string", notes = "transaction type")
    private String typeOfTransaction;


    @Min(1)
    @ApiModelProperty(required = true, dataType = "long", notes = "count")
    private Long count;

    @NotNull
    @NotEmpty
    @Size(min = 19, max = 19)
    @ApiModelProperty(required = true, dataType = "string", notes = "card number")
    private String fromCardNumber;
}