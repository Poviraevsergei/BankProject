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
@ApiModel("Bank updation model")
public class UpdateBankRequest extends CreateBankRequest {

    @ApiModelProperty(required = true, dataType = "long", notes = "bank id")
    private Long id;
}
