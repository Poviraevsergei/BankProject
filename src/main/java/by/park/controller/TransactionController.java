package by.park.controller;

import by.park.controller.request.PayingTransactionRequest;
import by.park.controller.request.TransferTransactionalRequest;
import by.park.domain.Transaction;
import by.park.service.TransactionService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    TransactionService transactionService;


    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    @ApiOperation(value = "Finding all transactions")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful loading transactions"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public List<Transaction> findAllTransactions() {
        return transactionService.findAllTransactions();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Finding transactions by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful card loading"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Transaction findTransactionById(@PathVariable("id") Long id) {
        return transactionService.findTransactionById(id);
    }

    @GetMapping("/info")
    @ApiOperation(value = "Information about User transactions")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public List<Transaction> transactionInformation(Principal principal) {
        return transactionService.transactionInformation(principal);
    }

    @PostMapping("/paying")
    @ApiOperation(value = "Paying for something")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public String paying(@Valid @RequestBody PayingTransactionRequest payingTransactionRequest, Principal principal) {
        return transactionService.paying(payingTransactionRequest, principal);
    }

    @PutMapping("/transfer")
    @ApiOperation(value = "Transfer money from card to card")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public String transfer(@Valid @RequestBody TransferTransactionalRequest transferTransactionalRequest, Principal principal) {
        return transactionService.transfer(transferTransactionalRequest, principal);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete transaction")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Card was deleted"),
            @ApiResponse(code = 204, message = "card was deleted"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public void deleteTransactionById(@PathVariable("id") Long id) {
        transactionService.deleteTransactionById(id);
    }
}
