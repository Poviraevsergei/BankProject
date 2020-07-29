package by.park.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Bank account updating model")
public class UpdateBankAccountRequest extends CreateBankAccountRequest {

    @ApiModelProperty(required = true, dataType = "long", notes = "bank account id")
    private long id;

    @NotEmpty
    @NotNull
    @Size(min = 33, max = 33)
    @ApiModelProperty(dataType = "string", required = true, notes = "bank IBAN")
    private String IBAN;

    @Min(1)
    @ApiModelProperty(dataType = "long", required = true, notes = "amount money on bank acccount")
    private Long amount;

    @Min(1)
    @ApiModelProperty(required = true, dataType = "long", notes = "bank id")
    private Long idBank;
}