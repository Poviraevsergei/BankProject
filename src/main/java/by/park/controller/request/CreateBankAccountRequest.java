package by.park.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("Bank account creation model")
public class CreateBankAccountRequest {

    @Min(1)
    @ApiModelProperty(dataType = "long", required = true, notes = "amount money on bank acccount")
    private Long amount;

    @Min(1)
    @ApiModelProperty(required = true, dataType = "long", notes = "user id")
    private Long idUser;

    @Min(1)
    @ApiModelProperty(required = true, dataType = "long", notes = "bank id")
    private Long idBank;
}
