package by.park.controller;

import by.park.controller.request.CreateBankRequest;
import by.park.controller.request.UpdateBankRequest;
import by.park.domain.Bank;
import by.park.exeption.CreationException;
import by.park.exeption.ResourceNotFoundException;
import by.park.service.BankService;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiImplicitParam;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/banks")
@ApiResponses({
        @ApiResponse(code = 401, message = "Don't have authorization"),
        @ApiResponse(code = 403, message = "Don't have authority"),
        @ApiResponse(code = 404, message = "Resource not found")
})
public class BankController {

    BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping
    @ApiOperation(value = "Finding all banks")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful loading banks"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token"),
            @ApiImplicitParam(name = "page", value = "Page number", example = "0", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Items per page", example = "3", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "Field to sort", example = "id", dataType = "string", paramType = "query")
    })
    public Page<Bank> findAllBanks(@ApiIgnore Pageable pageable) {
        return bankService.findAllBanks(pageable);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Finding bank by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Bank found successfully by id"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Optional<Bank> findBankById(@PathVariable("id") Long id) {
        Bank bank = Optional.ofNullable(bankService.findBankById(id)).orElseThrow(() -> new ResourceNotFoundException("Card not found!"));
        return Optional.of(bank);
    }

    @GetMapping("/find-bank-by-code")
    @ApiOperation(value = "Finding bank by bank code")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Bank found successfully by bank code"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Optional<Bank> findBankByBankCode(@RequestParam("BankCode") String bankCode) {
        Bank bank = Optional.ofNullable(bankService.findBankByBankCode(bankCode)).orElseThrow(() -> new ResourceNotFoundException("Bank not found!"));
        return Optional.of(bank);
    }

    @GetMapping("/find-bank-by-name")
    @ApiOperation(value = "Finding bank by name")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Bank found successfully by name"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Optional<Bank> findBankByName(@RequestParam("bankName") String bankName) {
        Bank bank = Optional.ofNullable(bankService.findBankByName(bankName)).orElseThrow(() -> new ResourceNotFoundException("Bank not found!"));
        return Optional.of(bank);
    }

    @GetMapping("/info")
    @ApiOperation(value = "Information about User bank")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Information loaded successfully"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Optional<List<Bank>> bankInformation(Principal principal) {
        return Optional.ofNullable(bankService.bankInformation(principal));
    }

    @PostMapping
    @ApiOperation(value = "Creating bank")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Bank has been successfully created"),
            @ApiResponse(code = 201, message = "Bank has been successfully created"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Bank createBank(@Valid @RequestBody CreateBankRequest createBankRequest) {
        Bank bank = bankService.createBank(createBankRequest);
        return bank;
    }

    @PutMapping
    @ApiOperation(value = "Updating bank")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Bank has been successfully updated"),
            @ApiResponse(code = 201, message = "Bank has been successfully updated"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Optional<Bank> updateBank(@Valid @RequestBody UpdateBankRequest updateBankRequest) {
        Bank bank = Optional.ofNullable(bankService.updateBank(updateBankRequest)).orElseThrow(() -> new ResourceNotFoundException("Bank not found!"));
        return Optional.of(bank);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleting bank")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Bank was deleted successfully"),
            @ApiResponse(code = 204, message = "Bank was deleted successfully"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public void deleteBankById(@PathVariable("id") Long id) {
        bankService.findBankById(id);
        bankService.deleteBankById(id);
    }
}