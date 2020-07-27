package by.park.controller;

import by.park.controller.request.CreateBankAccountRequest;
import by.park.controller.request.UpdateBankAccountRequest;
import by.park.domain.BankAccount;
import by.park.service.BankAccountService;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/bankAccounts")
@ApiResponses({
        @ApiResponse(code = 401, message = "Don't have authorization"),
        @ApiResponse(code = 403, message = "Don't have authority"),
        @ApiResponse(code = 404, message = "Resource not found")
})
public class BankAccountController {
    BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping
    @ApiOperation(value = "Finding all bank accounts")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful loading bank accounts"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token"),
            @ApiImplicitParam(name = "page", value = "Page number", example = "0", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Items per page", example = "3", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "Field to sort", example = "id", dataType = "string", paramType = "query")
    })
    public Page<BankAccount> findAll(@ApiIgnore Pageable pageable) {
        return bankAccountService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Finding bank account by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful bank account loading"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public BankAccount findById(@PathVariable("id") Long id) {
        return bankAccountService.findById(id);
    }

    @GetMapping("/info")
    @ApiOperation(value = "Information about User bank accounts")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful bank accounts loading"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public List<BankAccount> bankAccountInformation(Principal principal) {
        return bankAccountService.bankAccountInformation(principal);
    }

    @GetMapping("/find-by-IBAN")
    @ApiOperation(value = "Finding bank account by IBAN")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful bank account loading"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public BankAccount findBankAccountByIban(@RequestParam String IBAN) {
        return bankAccountService.findBankAccountByIban(IBAN);
    }

    @PostMapping("/create")
    @ApiOperation(value = "Creating bank account")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful bank account created"),
            @ApiResponse(code = 201, message = "Successful bank account created")
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public BankAccount createBankAccount(@Valid @RequestBody CreateBankAccountRequest createBankAccountRequest) {
        return bankAccountService.createBankAccount(createBankAccountRequest);
    }

    @PutMapping
    @ApiOperation(value = "Updating bank account")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful bank account updating"),
            @ApiResponse(code = 201, message = "Successful bank account updating")
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public BankAccount updateBankAccount(@Valid @RequestBody UpdateBankAccountRequest updateBankAccountRequest) {
        return bankAccountService.updateBankAccount(updateBankAccountRequest);
    }

    @DeleteMapping
    @ApiOperation(value = "Deleting bank account")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Bank account was deleted"),
            @ApiResponse(code = 204, message = "Bank account was deleted")
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public void deleteBankAccountById(Long id) {
        bankAccountService.deleteBankAccountById(id);
    }
}