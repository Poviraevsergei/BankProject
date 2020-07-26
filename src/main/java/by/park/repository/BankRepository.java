package by.park.repository;

import by.park.domain.Bank;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@CacheConfig(cacheNames = "banks")
@Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public interface BankRepository extends JpaRepository<Bank, Long> {

    Bank findBankByBankName(String bankName);

    @Modifying
    @Query("delete from Bank b where b.id = :id")
    void deleteBankById(@Param("id") Long id);

    @Cacheable
    Bank findBankByBankCode(String bankCode);
}
