package by.park.controller;

import by.park.controller.request.CreateBankRequest;
import by.park.controller.request.UpdateBankRequest;
import by.park.domain.Bank;
import by.park.service.BankService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/banks")
public class BankController {

    BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Finding bank by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful bank loading"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Bank findBankById(@PathVariable("id") Long id) {
        return bankService.findBankById(id);
    }

    @GetMapping("/find-bank-by-code")
    @ApiOperation(value = "Finding bank by bank code")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful bank loading"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Bank findBankByBankCode(@RequestParam("BankCode") String bankCode) {
        return bankService.findBankByBankCode(bankCode);
    }

    @GetMapping("/find-bank-by-name")
    @ApiOperation(value = "Finding bank by name")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful bank loading"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Bank findBankByName(@RequestParam("bankName") String bankName) {
        return bankService.findBankByName(bankName);
    }

    @GetMapping
    @ApiOperation(value = "Finding all banks")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful loading banks"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public List<Bank> findAllBanks() {
        return bankService.findAllBanks();
    }

    @GetMapping("/info")
    @ApiOperation(value = "Information about User bank")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public List<Bank> bankInformation(Principal principal) {
        return bankService.bankInformation(principal);
    }

    @PostMapping
    @ApiOperation(value = "Creating bank")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful bank creating"),
            @ApiResponse(code = 201, message = "Successful bank creating"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Bank createBank(@Valid @RequestBody CreateBankRequest createBankRequest) {
        return bankService.createBank(createBankRequest);
    }

    @PutMapping
    @ApiOperation(value = "Updating bank")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful bank updating"),
            @ApiResponse(code = 201, message = "Successful bank updating"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Bank updateBank(@Valid @RequestBody UpdateBankRequest updateBankRequest) {
        return bankService.updateBank(updateBankRequest);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleting bank")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Bank was deleted"),
            @ApiResponse(code = 204, message = "Bank was deleted"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public void deleteBankById(@PathVariable("id") Long id) {
        bankService.deleteBankById(id);
    }
}
