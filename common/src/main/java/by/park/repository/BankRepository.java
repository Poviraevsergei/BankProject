package by.park.repository;

import by.park.domain.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public interface BankRepository extends JpaRepository<Bank, Long>, CrudRepository<Bank, Long>, PagingAndSortingRepository<Bank, Long> {

    Bank findBankByBankName(String bankName);

    @Modifying
    @Query("delete from Bank b where b.id = :id")
    void deleteBankById(@Param("id") Long id);

    Bank findBankByBankCode(String bankCode);
}