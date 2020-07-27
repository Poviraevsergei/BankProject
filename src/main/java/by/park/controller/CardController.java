package by.park.controller;

import by.park.controller.request.CreateCardRequest;
import by.park.controller.request.UpdateCardRequest;
import by.park.domain.Card;
import by.park.service.CardService;
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

    @GetMapping("/{id}")
    @ApiOperation(value = "Finding card by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Card found successfully by id"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Optional<Card> findCardById(@PathVariable("id") Long id) {
        return Optional.ofNullable(cardService.findCardById(id));
    }

    @GetMapping("/find-by-card-number")
    @ApiOperation(value = "Finding card by card number")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Card found successfully by card number"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Optional<Card> findCardByCardNumber(@RequestParam("CardNumber") String cardNumber) {
        return Optional.ofNullable(cardService.findByCardNumber(cardNumber));
    }

    @GetMapping("/info")
    @ApiOperation(value = "Information about User cards")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Information loaded successfully"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Optional<List<Card>> cardInformation(Principal principal) {
        return Optional.ofNullable(cardService.cardInformation(principal));
    }

    @PostMapping("/create")
    @ApiOperation(value = "Creating card")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Card has been successfully created"),
            @ApiResponse(code = 201, message = "Card has been successfully created"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Optional<Card> createCard(@Valid @RequestBody CreateCardRequest createCardRequest, Principal principal) {
        return Optional.ofNullable(cardService.createCard(createCardRequest, principal));
    }

    @PutMapping
    @ApiOperation(value = "Updating card")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Card has been successfully updated"),
            @ApiResponse(code = 201, message = "Card has been successfully updated"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Optional<Card> updateCard(@Valid @RequestBody UpdateCardRequest updateCardRequest) {
        return Optional.ofNullable(cardService.updateCard(updateCardRequest));
    }

    @PutMapping("/blocked")
    @ApiOperation(value = "Blocked card")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Card has been successfully blocked"),
            @ApiResponse(code = 204, message = "Card has been successfully blocked"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Optional<Card> blockedCard(@RequestParam("cardNUmber") String cardNUmber, Principal principal) {
        return Optional.ofNullable(cardService.blockedCard(cardNUmber, principal));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleting card")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Card was deleted successfully"),
            @ApiResponse(code = 204, message = "Card was deleted successfully"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public void deleteCardById(@PathVariable("id") Long id) {
        cardService.deleteCardById(id);
    }
}