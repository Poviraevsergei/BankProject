package by.park.controller;

import by.park.controller.request.CreateBankRequest;
import by.park.controller.request.UpdateBankRequest;
import by.park.domain.Bank;
import by.park.service.BankService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bank")
public class BankController {

    BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping("/{id}")
    public Optional<Bank> findBankById(@PathVariable("id") Long id) {
        return bankService.findBankById(id);
    }

    @GetMapping("/code")
    Bank findBankByBankCode(@RequestParam("code") String bankCode) {
        return bankService.findBankByBankCode(bankCode);
    }

    @GetMapping
    List<Bank> findAllBanks() {
        return bankService.findAllBanks();
    }

    @PostMapping
    Bank createBank(@Valid @RequestBody CreateBankRequest createBankRequest) {
        return bankService.createBank(createBankRequest);
    }

    @PutMapping
    Bank updateBank(@Valid @RequestBody UpdateBankRequest updateBankRequest) {
        return bankService.updateBank(updateBankRequest);
    }

    @DeleteMapping
    void deleteBankById(Long id) {
        bankService.deleteBankById(id);
    }

    @GetMapping("/name")
    public Bank findBankByName(@RequestParam("name") String bankName) {
        return bankService.findBankByName(bankName);
    }
}
