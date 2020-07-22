package by.park.controller;

import by.park.controller.request.CreateBankAccountRequest;
import by.park.controller.request.UpdateBankAccountRequest;
import by.park.domain.BankAccount;
import by.park.service.BankAccountService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bankAccount")
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
    Optional<BankAccount> findById(@PathVariable("id") Long id) {
        return bankAccountService.findById(id);
    }

    @GetMapping("/find-by-IBAN")
    @ApiOperation(value = "Finding bank account by IBAN")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful bank account loading"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    BankAccount findBankAccountByIban(@RequestParam String IBAN) {
        return bankAccountService.findBankAccountByIban(IBAN);
    }

    @PostMapping
    @ApiOperation(value = "Creating bank account")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful bank account creating"),
            @ApiResponse(code = 201, message = "Successful bank account creating"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    BankAccount createBankAccount(@RequestBody CreateBankAccountRequest createBankAccountRequest) {
        return bankAccountService.createBankAccount(createBankAccountRequest);
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
    BankAccount updateBankAccount(@RequestBody UpdateBankAccountRequest updateBankAccountRequest) {
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
    void deleteBankAccountById(Long id) {
        bankAccountService.deleteBankAccountById(id);
    }
}
