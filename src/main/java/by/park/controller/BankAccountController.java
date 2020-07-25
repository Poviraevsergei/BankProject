package by.park.controller;

import by.park.controller.request.CreateBankAccountRequest;
import by.park.controller.request.UpdateBankAccountRequest;
import by.park.domain.BankAccount;
import by.park.service.BankAccountService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/bankAccounts")
public class BankAccountController {
    BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping
    @ApiOperation(value = "Finding all bank accounts")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful loading bank accounts"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    List<BankAccount> findAll() {
        return bankAccountService.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Finding bank account by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful bank account loading"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    BankAccount findById(@PathVariable("id") Long id) {
        return bankAccountService.findById(id);
    }

    @GetMapping("/info")
    @ApiOperation(value = "Information about User bank accounts")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful bank accounts loading"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public List<BankAccount> bankAccountInformation(Principal principal) {
        return bankAccountService.bankAccountInformation(principal);
    }

    @GetMapping("/find-by-IBAN")
    @ApiOperation(value = "Finding bank account by IBAN")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful bank account loading"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    BankAccount findBankAccountByIban(@RequestParam String IBAN) {
        return bankAccountService.findBankAccountByIban(IBAN);
    }

    @PostMapping("/create")
    @ApiOperation(value = "Creating bank account")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful bank account created"),
            @ApiResponse(code = 201, message = "Successful bank account created"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    BankAccount createBankAccount(@Valid @RequestBody CreateBankAccountRequest createBankAccountRequest, Principal principal) {
        return bankAccountService.createBankAccount(createBankAccountRequest, principal);
    }

    @PutMapping
    @ApiOperation(value = "Updating bank account")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful bank account updating"),
            @ApiResponse(code = 201, message = "Successful bank account updating"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    BankAccount updateBankAccount(@Valid @RequestBody UpdateBankAccountRequest updateBankAccountRequest) {
        return bankAccountService.updateBankAccount(updateBankAccountRequest);
    }

    @DeleteMapping
    @ApiOperation(value = "Deleting bank account")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Bank account was deleted"),
            @ApiResponse(code = 204, message = "Bank account was deleted"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    void deleteBankAccountById(Long id) {
        bankAccountService.deleteBankAccountById(id);
    }
}
