package by.park.repository;

import by.park.domain.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BankRepository extends JpaRepository<Bank, Long> {
    Bank findBankByBankName(String bankName);

    @Modifying
    @Query("update Bank b set b.deleted = true where b.id = :id")
    void deleteBankById(@Param("id") Long id);

    Bank findBankByBankCode(String bankCode);
}
