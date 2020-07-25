package by.park.service;

import by.park.controller.request.CreateBankRequest;
import by.park.controller.request.UpdateBankRequest;
import by.park.domain.Bank;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface BankService {
    List<Bank> findAllBanks();

    Bank findBankById(Long id);

    Bank findBankByName(String bankName);

    Bank findBankByBankCode(String bankCode);

    Bank createBank(CreateBankRequest createBankRequest);

    Bank updateBank(UpdateBankRequest updateBankRequest);

    List<Bank> bankInformation(Principal principal);

    void deleteBankById(Long id);
}
