package by.park.controller;

import by.park.controller.request.PayingTransactionRequest;
import by.park.controller.request.TransferTransactionalRequest;
import by.park.domain.Transaction;
import by.park.exeption.ResourceNotFoundException;
import by.park.service.TransactionService;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
@RequestMapping("/transactions")
@ApiResponses({
        @ApiResponse(code = 401, message = "Don't have authorization"),
        @ApiResponse(code = 403, message = "Don't have authority"),
        @ApiResponse(code = 404, message = "Resource not found")
})
public class TransactionController {

    TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    @ApiOperation(value = "Finding all transactions")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful loading transactions"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token"),
            @ApiImplicitParam(name = "page", value = "Page number", example = "0", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Items per page", example = "3", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "Field to sort", example = "id", dataType = "string", paramType = "query")
    })
    public Page<Transaction> findAllTransactions(@ApiIgnore Pageable pageable) {
        return transactionService.findAllTransactions(pageable);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Finding transactions by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Transaction found successfully by id"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Optional<Transaction> findTransactionById(@PathVariable("id") Long id) {
        Transaction transaction = Optional.ofNullable(transactionService.findTransactionById(id)).orElseThrow(() -> new ResourceNotFoundException("Transaction not found!"));
        return Optional.of(transaction);
    }

    @GetMapping("/info")
    @ApiOperation(value = "Information about User transactions")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Information loaded successfully"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Optional<List<Transaction>> transactionInformation(Principal principal) {
        return Optional.ofNullable(transactionService.transactionInformation(principal));
    }

    @PostMapping("/paying")
    @ApiOperation(value = "Paying for something")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Payment was successful"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public String paying(@Valid @RequestBody PayingTransactionRequest payingTransactionRequest, Principal principal) {
        return transactionService.paying(payingTransactionRequest, principal);
    }

    @PutMapping("/transfer")
    @ApiOperation(value = "Transfer money from card to card")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Transfer of money was successful"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public String transfer(@Valid @RequestBody TransferTransactionalRequest transferTransactionalRequest, Principal principal) {
        return transactionService.transfer(transferTransactionalRequest, principal);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete transaction")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Transaction was deleted successfully"),
            @ApiResponse(code = 204, message = "Transaction was deleted successfully"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public void deleteTransactionById(@PathVariable("id") Long id) {
        transactionService.findTransactionById(id);
        transactionService.deleteTransactionById(id);
    }
}