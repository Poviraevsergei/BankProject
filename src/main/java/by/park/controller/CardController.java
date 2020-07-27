package by.park.controller;

import by.park.controller.request.CreateCardRequest;
import by.park.controller.request.UpdateCardRequest;
import by.park.domain.Card;
import by.park.security.util.PrincipalUtil;
import by.park.service.CardService;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/cards")
@ApiResponses({
        @ApiResponse(code = 401, message = "Don't have authorization"),
        @ApiResponse(code = 403, message = "Don't have authority"),
        @ApiResponse(code = 404, message = "Resource not found")
})
public class CardController {
    CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    @ApiOperation(value = "Finding all cards")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful loading cards"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token"),
            @ApiImplicitParam(name = "page", value = "Page number", example = "0", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Items per page", example = "3", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "Field to sort", example = "id", dataType = "string", paramType = "query")
    })
    public Page<Card> findAll(@ApiIgnore Pageable pageable) {
        return cardService.findAll(pageable);
    }


    @GetMapping("/info")
    @ApiOperation(value = "Information about User cards")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public List<Card> cardInformation(Principal principal) {
        return cardService.cardInformation(principal);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Finding card by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful card loading"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Card findCardById(@PathVariable("id") Long id) {
        return cardService.findCardById(id);
    }

    @GetMapping("/find-by-card-number")
    @ApiOperation(value = "Finding card by card number")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful card loading"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Card findCardByCardNumber(@RequestParam("CardNumber") String cardNumber) {
        return cardService.findByCardNumber(cardNumber);
    }

    @PostMapping("/create")
    @ApiOperation(value = "Creating card")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful card creating"),
            @ApiResponse(code = 201, message = "Successful card creating"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Card createCard(@Valid @RequestBody CreateCardRequest createCardRequest, Principal principal) {
        return cardService.createCard(createCardRequest, principal);
    }

    @PutMapping
    @ApiOperation(value = "Updating card")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful card updating"),
            @ApiResponse(code = 201, message = "Successful card updating"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Card updateCard(@Valid @RequestBody UpdateCardRequest updateCardRequest) {
        return cardService.updateCard(updateCardRequest);
    }

    @PutMapping("/blocked")
    @ApiOperation(value = "Blocked card")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Card was blocked"),
            @ApiResponse(code = 204, message = "card was blocked"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Card blockedCard(@RequestParam("cardNUmber") String cardNUmber, Principal principal) {
        return cardService.blockedCard(cardNUmber, principal);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleting card")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Card was deleted"),
            @ApiResponse(code = 204, message = "card was deleted"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public void deleteCardById(@PathVariable("id") Long id) {
        cardService.deleteCardById(id);
    }
}