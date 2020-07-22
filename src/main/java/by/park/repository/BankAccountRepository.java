package by.park.repository;

import by.park.domain.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    BankAccount findBankAccountByIban(String IBAN);

    @Modifying
    @Query("delete from BankAccount b where b.id = :id")
    void deleteBankAccountById(@Param("id") Long id);
}
