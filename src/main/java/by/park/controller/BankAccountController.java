package by.park.controller;

import by.park.controller.request.CreateBankAccountRequest;
import by.park.controller.request.UpdateBankAccountRequest;
import by.park.domain.BankAccount;
import by.park.service.BankAccountService;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

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
            @ApiResponse(code = 200, message = "Bank accounts loaded successfully"),
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
            @ApiResponse(code = 200, message = "Bank account found successfully by id"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Optional<BankAccount> findById(@PathVariable("id") Long id) {
        return Optional.ofNullable(bankAccountService.findById(id));
    }

    @GetMapping("/find-by-IBAN")
    @ApiOperation(value = "Finding bank account by IBAN")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Bank account found successfully by IBAN"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Optional<BankAccount> findBankAccountByIban(@RequestParam String IBAN) {
        return Optional.ofNullable(bankAccountService.findBankAccountByIban(IBAN));
    }

    @GetMapping("/info")
    @ApiOperation(value = "Information about User bank accounts")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Information loaded successfully"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Optional<List<BankAccount>> bankAccountInformation(Principal principal) {
        return Optional.ofNullable(bankAccountService.bankAccountInformation(principal));
    }


    @PostMapping("/create")
    @ApiOperation(value = "Creating bank account")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Bank account has been successfully created"),
            @ApiResponse(code = 201, message = "Bank account has been successfully created")
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Optional<BankAccount> createBankAccount(@Valid @RequestBody CreateBankAccountRequest createBankAccountRequest) {
        return Optional.ofNullable(bankAccountService.createBankAccount(createBankAccountRequest));
    }

    @PutMapping
    @ApiOperation(value = "Updating bank account")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Bank account has been successfully updated"),
            @ApiResponse(code = 201, message = "Bank account has been successfully updated")
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Optional<BankAccount> updateBankAccount(@Valid @RequestBody UpdateBankAccountRequest updateBankAccountRequest) {
        return Optional.ofNullable(bankAccountService.updateBankAccount(updateBankAccountRequest));
    }

    @DeleteMapping
    @ApiOperation(value = "Deleting bank account")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Bank account was deleted successfully"),
            @ApiResponse(code = 204, message = "Bank account was deleted successfully")
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public void deleteBankAccountById(Long id) {
        bankAccountService.deleteBankAccountById(id);
    }
}