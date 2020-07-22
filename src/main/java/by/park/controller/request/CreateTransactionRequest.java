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
@ApiModel("Transaction creation model")
public class CreateTransactionRequest {

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 100)
    @ApiModelProperty(required = true, dataType = "string", notes = "transaction type")
    private String typeOfTransaction;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 100)
    @ApiModelProperty(required = true, dataType = "long", notes = "count")
    private Long count;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 100)
    @ApiModelProperty(required = true, dataType = "long", notes = "bank account IBAN")
    private String IBANBankAccount;
}
