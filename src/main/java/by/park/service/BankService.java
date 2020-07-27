package by.park.service;

import by.park.controller.request.CreateBankRequest;
import by.park.controller.request.UpdateBankRequest;
import by.park.domain.Bank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;

public interface BankService {
    Page<Bank> findAllBanks(Pageable pageable);

    Bank findBankById(Long id);

    Bank findBankByName(String bankName);

    Bank findBankByBankCode(String bankCode);

    Bank createBank(CreateBankRequest createBankRequest);

    Bank updateBank(UpdateBankRequest updateBankRequest);

    List<Bank> bankInformation(Principal principal);

    void deleteBankById(Long id);
}
