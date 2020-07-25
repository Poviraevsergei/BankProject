package by.park.controller;

import by.park.controller.request.CreateCardRequest;
import by.park.controller.request.UpdateCardRequest;
import by.park.domain.Card;
import by.park.service.CardService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {
    CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    @ApiOperation(value = "Finding all cards")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful loading cards"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    List<Card> findAll() {
        return cardService.findAll();
    }


    @GetMapping("/info")
    @ApiOperation(value = "Information about User cards")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public List<Card> cardInformation(Principal principal) {
        return cardService.cardInformation(principal);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Finding card by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful card loading"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    Card findCardById(@PathVariable("id") Long id) {
        return cardService.findCardById(id);
    }

    @GetMapping("/find-by-card-number")
    @ApiOperation(value = "Finding card by card number")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful card loading"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    Card findCardByCardNumber(@RequestParam("CardNumber") String cardNumber) {
        return cardService.findByCardNumber(cardNumber);
    }

    @PostMapping
    @ApiOperation(value = "Creating card")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful card creating"),
            @ApiResponse(code = 201, message = "Successful card creating"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    Card createCard(@Valid @RequestBody CreateCardRequest createCardRequest) {
        return cardService.createCard(createCardRequest);
    }

    @PutMapping
    @ApiOperation(value = "Updating card")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful card updating"),
            @ApiResponse(code = 201, message = "Successful card updating"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    Card updateCard(@Valid @RequestBody UpdateCardRequest updateCardRequest) {
        return cardService.updateCard(updateCardRequest);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleting card")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Card was deleted"),
            @ApiResponse(code = 204, message = "card was deleted"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    void deleteCardById(@PathVariable("id") Long id) {
        cardService.deleteCardById(id);
    }
}